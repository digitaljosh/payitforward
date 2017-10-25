package com.example.payitforward.models;

import com.example.payitforward.models.User;

import javax.persistence.Entity;

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

    private String name;

    private String description;

    @ManyToOne
    private User user;



    private String location;


    private int claimed;






//
//    private List<User> completingUsers;




 //   @OneToMany
    private Boolean completed = false;

    private Boolean usersOnly = false;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getClaimed() {
        return claimed;
    }

    public void setClaimed(int claimed) {
        this.claimed = claimed;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getUsersOnly() {
        return usersOnly;
    }

    public void setUsersOnly(Boolean usersOnly) {
        this.usersOnly = usersOnly;
    }


//
//    public List<User> getCompletingUsers() {return completingUsers; }
//
//    public void setCompletingUsers(List<User> completingUsers) { this.completingUsers = completingUsers; }


}
