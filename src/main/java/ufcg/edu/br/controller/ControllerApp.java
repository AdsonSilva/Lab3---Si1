package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ufcg.edu.br.model.SubTask;
import ufcg.edu.br.model.Task;
import ufcg.edu.br.model.TodoList;
import ufcg.edu.br.repository.SubTaskRepository;
import ufcg.edu.br.repository.TaskRepository;
import ufcg.edu.br.repository.TodoListRepository;

import java.util.List;

/**
 * Created by adson_silva on 05/02/17.
 */
@CrossOrigin
@Controller
public class ControllerApp {
    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model){
        List<TodoList> todoLists = todoListRepository.findAll();
        model.addAttribute("todoLists", todoLists );
        model.addAttribute("tasksNotCompleteds", getNumOfTasksNotCompleted(todoLists));


        return "index";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String getTasks(Model model){
        return "tasks";
    }

    @RequestMapping(value = "todoList/createTodo", method = RequestMethod.GET)
    public String addTodo(Model model){
        return "createTodo";
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public String deleteAllTodo(Model model){
        subTaskRepository.deleteAll();
        taskRepository.deleteAll();
        todoListRepository.deleteAll();

        List<TodoList> todoLists = todoListRepository.findAll();
        model.addAttribute("todoLists", todoLists );
        model.addAttribute("tasksNotCompleteds", getNumOfTasksNotCompleted(todoLists));
        return "redirect:index.html";
    }

    @RequestMapping(value = "/createTodo", method = RequestMethod.POST)
    public String addTodoPost(@ModelAttribute TodoList todoList,Model model){
        todoListRepository.save(todoList);
        return "redirect:index.html";
    }

    @RequestMapping(value = "/todoList/all", method = RequestMethod.GET)
    public List<TodoList> getAllTodoList() {
        return todoListRepository.findAll();
    }

    @RequestMapping(value = "/todoList/getById/{id}", method = RequestMethod.GET)
    public TodoList getTodoListById(@PathVariable String id) {
        return todoListRepository.findOne(id);
    }

    @RequestMapping(value = "/todoList/create", method = RequestMethod.POST)
    public List<TodoList> createTodoList(@RequestBody TodoList todoList) {
        todoListRepository.save(todoList);

        return todoListRepository.findAll();
    }

    @RequestMapping(value = "/todoList/getByName/{name}", method = RequestMethod.GET)
    public TodoList getTodoListByName(@PathVariable String name) {
        return todoListRepository.findByName(name);
    }

    @RequestMapping(value = "/deleteTodoList/{id}", method = RequestMethod.GET)
    public RedirectView deleteTodoListById(@PathVariable String id, Model model) {
        deleteTasksByIdList(id);

        todoListRepository.delete(id);

        List<TodoList> todoLists = todoListRepository.findAll();
        model.addAttribute("todoLists", todoLists );
        model.addAttribute("tasksNotCompleteds", getNumOfTasksNotCompleted(todoLists));

        return new RedirectView("../index.html");

    }


    @RequestMapping(value = "/task/all", method = RequestMethod.GET)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @RequestMapping(value = "/task/getById/{id}", method = RequestMethod.GET)
    public Task getTaskById(@PathVariable String id) {
        return taskRepository.findOne(id);
    }

    @RequestMapping(value = "/task/getByList/{idList}", method = RequestMethod.GET)
    public String getTasksByList(@PathVariable String idList, Model model) {
        model.addAttribute("tasks", taskRepository.findByIdList(idList));
        model.addAttribute("todoListName", todoListRepository.findById(idList).getName());
        return "tasks";
    }

    @RequestMapping(value = "/task/create/{idList}", method = RequestMethod.GET)
    public String createTaskView(Model model, @PathVariable String idList){
        System.out.println(idList);
        model.addAttribute("idList", idList);
        return "createTask";
    }

    @RequestMapping(value = "/task/create/{idList}", method = RequestMethod.POST)
    public String createTask(@ModelAttribute Task task, @PathVariable String idList, Model model){
        System.out.println(idList);
        task.setIdList(idList);
        taskRepository.save(task);
        return "redirect:/task/getByList/" + idList;
    }

    @RequestMapping(value = "task/complete/{id}", method = RequestMethod.GET)
    public String completeTask(@PathVariable String id, Model model){
        Task task = taskRepository.findById(id);
        taskRepository.delete(task.getId());

        if(task.getStatus()){
            task.setStatus(false);
        }else{
            task.setStatus(true);
        }

        taskRepository.save(task);

        return "redirect:/task/getByList/" + task.getIdList();
    }

    @RequestMapping(value = "/task/delete/{id}", method = RequestMethod.GET)
    public RedirectView deleteTaskById(@PathVariable String id, Model model) {
        Task task = taskRepository.findById(id);
        deleteSubTaskById(id);
        taskRepository.delete(id);

        return new RedirectView("/task/getByList/" + task.getIdList());
    }

    private void deleteTasksByIdList(String idList){
        for (Task task : taskRepository.findAll()){
            if(task.getIdList().equals(idList)){
                deleteSubTasksByIdTask(task.getId());
                deleteTaskById(task.getId());
            }
        }
    }

    private void deleteTaskById(String id) {
        deleteSubTaskById(id);
        taskRepository.delete(id);

    }



    @RequestMapping(value = "/subTask/all", method = RequestMethod.GET)
    public List<SubTask> getAllSubTasks(){
        return subTaskRepository.findAll();
    }

    @RequestMapping(value = "/subTask/getById/{id}", method = RequestMethod.GET)
    public SubTask getSubTaskById(@PathVariable String id){
        return subTaskRepository.findOne(id);
    }

    @RequestMapping(value = "/subTask/create", method = RequestMethod.POST)
    public List<SubTask> createSubTask(@RequestBody SubTask task){
        subTaskRepository.save(task);

        return subTaskRepository.findAll();
    }

    @RequestMapping(value = "/subTask/getByList/{idTask}", method = RequestMethod.GET)
    public List<SubTask> getSubTasksByTask(@PathVariable String idTask){
        return subTaskRepository.findByIdTask(idTask);
    }


    @RequestMapping(value = "/subTask/delete/{id}", method = RequestMethod.DELETE)
    public List<SubTask> deleteSubTaskById(@PathVariable String id){
        subTaskRepository.delete(id);

        return subTaskRepository.findAll();
    }

    private void deleteSubTasksByIdTask(String idTask) {
        for (SubTask subTask : subTaskRepository.findAll()) {
            if (subTask.getIdTask().equals(idTask)) {
                deleteSubTaskById(subTask.getId());
            }
        }

    }

    private int getNumOfTasksNotCompleted(List<TodoList> todoLists){
        int tasksNotCompleted = 0;
        for(TodoList todolist : todoLists){
            for (Task task : taskRepository.findByIdList(todolist.getId())){
                if(task.getStatus() == false){
                    tasksNotCompleted ++;
                }
            }
        }
        return tasksNotCompleted;
    }
}
