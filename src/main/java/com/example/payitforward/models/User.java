package com.example.payitforward.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String userName;

    private String displayName;

    // TODO: password hash
    private String password;

    // TODO: add email field

    private String bio;

    // TODO: add profile picture

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Opportunity> opportunities = new ArrayList<>();

    public User(String userName, String displayName, String password, String bio) {
        this.userName = userName;
        this.displayName = displayName;
        this.password = password;
        this.bio = bio;
    }

    public User(String name) { this.userName = name; }

    public User() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public List<Opportunity> getOpportunities() { return opportunities; }

    public String getPassword() { return password; }
}
