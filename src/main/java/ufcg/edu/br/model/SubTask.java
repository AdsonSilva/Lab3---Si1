package ufcg.edu.br.model;

import org.springframework.data.annotation.Id;

/**
 * Created by adson_silva on 05/02/17.
 */
public class SubTask {

    @Id
    private String id;

    private String idTask;

    private String title;

    public SubTask(String id, String idTask, String title) {
        this.id = id;
        this.idTask = idTask;
        this.title = title;
    }

    public SubTask(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTask() {
        return idTask;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id='" + id + '\'' +
                ", idTask='" + idTask + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
