package ufcg.edu.br.model;

import org.springframework.data.annotation.Id;

/**
 * Created by adson_silva on 04/02/17.
 */
public class User {

    @Id
    private String id;

    private String nome;
    private String email;
    private String repoGit;

    public User(String nome, String email, String repoGit) {
        this.nome = nome;
        this.email = email;
        this.repoGit = repoGit;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepoGit() {
        return repoGit;
    }

    public void setRepoGit(String repoGit) {
        this.repoGit = repoGit;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", repoGit='" + repoGit + '\'' +
                '}';
    }
}
