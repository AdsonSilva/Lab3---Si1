package ufcg.edu.br.model;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by adson_silva on 04/02/17.
 */
public class TodoList {

    @Id
    private String id;

    private String name;

    public TodoList(String nome, List<Task> tasks) {
        this.name = nome;
    }

    public TodoList(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }


    @Override
    public String toString() {
        return "TodoList{" +
                "id='" + id + '\'' +
                ", nome='" + name + '\'' +
                +'}';
    }

}
