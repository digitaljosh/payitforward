package com.example.payitforward.models;

import com.sun.xml.internal.bind.v2.TODO;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 15)
    private String username;

    @NotNull
    @Size(min = 3, max = 15)
    private String displayname;

    // TODO: password hash
    @NotNull
    @Size(min = 3, max = 15)
    private String password;


    @NotNull
    @Size(min = 6, max = 25)
    private String email;

    @NotNull
    @Size(min = 15, max = 750)
    private String bio;

    // TODO: add profile picture



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
}

