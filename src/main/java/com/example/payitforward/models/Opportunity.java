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
    private User OpportunityCreator;

    private String location;

    private int claimed;

    @ManyToMany
    private List<User> claimingUsers;

    @ManyToMany
    private List<User> completingUsers;



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

    public void setClaimingUsers(List<User> claimingUsers) { this.claimingUsers = claimingUsers; }

    public List<User> getClaimingUsers() { return claimingUsers; }

    public User getOpportunityCreator() { return OpportunityCreator; }

    public void setOpportunityCreator(User opportunityCreator) { OpportunityCreator = opportunityCreator; }

    public List<User> getCompletingUsers() {return completingUsers; }

    public void setCompletingUsers(List<User> completingUsers) { this.completingUsers = completingUsers; }


}
