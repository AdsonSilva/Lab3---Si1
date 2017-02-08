package ufcg.edu.br.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ufcg.edu.br.model.SubTask;

import java.util.List;

/**
 * Created by adson_silva on 05/02/17.
 */
public interface SubTaskRepository extends MongoRepository<SubTask, String> {

    public List<SubTask> findByIdTask(String idTask);

}
