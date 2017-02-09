package ufcg.edu.br.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ufcg.edu.br.model.TodoList;

import java.util.List;

/**
 * Created by adson_silva on 04/02/17.
 */
public interface TodoListRepository extends MongoRepository<TodoList, String>{

    public TodoList findByName(String name);
    public TodoList findById(String id);
}
