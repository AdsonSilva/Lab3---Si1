package ufcg.edu.br.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ufcg.edu.br.model.Task;

import java.util.List;

/**
 * Created by adson_silva on 04/02/17.
 */
public interface TaskRepository extends MongoRepository<Task, String> {

   public Task findById(String id);
    public List<Task> findByCategory(String category);
    public List<Task> findByIdList(String idList);

}