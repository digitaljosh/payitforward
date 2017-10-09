package com.example.payitforward.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Opportunity {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=0, max=140)
    private String description;

    @ManyToOne
    private User user;

    @NotNull
    @Size(min=3, max=15)
    private String location;


    public Opportunity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Opportunity() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
