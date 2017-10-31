package com.example.payitforward.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String username;

    private String displayname;

    // TODO: password hash

    @NotNull
    @Size(min=3, max=15)
    private String password;

    private String email;

    private String bio;

    private String imageName;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Opportunity> opportunities = new ArrayList<>();


    public User(String name) { this.username = name; }

    public User() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String name) { this.username = name; }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public List<Opportunity> getOpportunities() { return opportunities; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
