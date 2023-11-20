package com.example.proiect;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    
    private String fromDate;

    private String toDate;

    private String path;

    private String company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDtls user;

    @ManyToOne
    @JoinColumn(name = "replacement_id")
    private UserDtls replacement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDtls getUser() {
        return user;
    }

    public void setUser(UserDtls user) {
        this.user = user;
    }

    public UserDtls getReplacement() {
        return replacement;
    }

    public void setReplacement(UserDtls replacement) {
        this.replacement = replacement;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
