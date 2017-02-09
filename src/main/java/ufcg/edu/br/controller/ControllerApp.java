package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("todoLists", todoListRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String getTasks(Model model){
        return "tasks";
    }

    @RequestMapping(value = "/createTodo", method = RequestMethod.GET)
    public String addTodo(Model model){
        return "createTodo";
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

    @RequestMapping(value = "/todoList/delete/{id}", method = RequestMethod.DELETE)
    public List<TodoList> deleteTodoListById(@PathVariable String id) {
        deleteTasksByIdList(id);

        todoListRepository.delete(id);
        return todoListRepository.findAll();
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

    @RequestMapping(value = "/task/create", method = RequestMethod.GET)
    public String createTaks(Model model){
        return "createTask";
    }

    @RequestMapping(value = "task/complete/{id}", method = RequestMethod.GET)
    public String completeTask(@PathVariable String id, Model model){
        Task task = taskRepository.findById(id);
        taskRepository.delete(task.getId());
        System.out.println(task.isStatus());
        if(task.isStatus()){
            task.setStatus(false);
        }else {
            task.setStatus(true);
        }
        taskRepository.save(task);

        return "redirect:/task/getByList/" + taskRepository.findById(id).getIdList();
    }

    @RequestMapping(value = "/task/create", method = RequestMethod.POST)
    public List<Task> createTask(@RequestBody Task task) {
        taskRepository.save(task);

        return taskRepository.findAll();
    }

    @RequestMapping(value = "/task/delete/{id}", method = RequestMethod.DELETE)
    public List<Task> deleteTaskById(@PathVariable String id) {
        taskRepository.delete(id);

        return taskRepository.findAll();
    }

    private void deleteTasksByIdList(String idList){
        for (Task task : taskRepository.findAll()){
            if(task.getIdList().equals(idList)){
                deleteSubTasksByIdTask(task.getId());
                deleteTaskById(task.getId());
            }
        }
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

}
