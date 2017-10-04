package com.example.payitforward.Models.Forms;

import javax.validation.constraints.NotNull;

public class EditUserForm {

    @NotNull
    private int userId;

    @NotNull
    private String name;

    @NotNull
    private String bio;

    public EditUserForm() { }

    public EditUserForm(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }
}
