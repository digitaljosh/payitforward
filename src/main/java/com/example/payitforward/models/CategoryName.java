package com.example.payitforward.models;

public enum CategoryName {
    MANUAL_LABOR ("Manual Labor"),
    YARD_WORK ("Yard Work"),
    LIGHT_CONSTRUCTION ("Light Construction"),
    PAINTING ("Painting"),
    COOKING ("Cooking"),
    HOUSE_CLEANING ("House Cleaning"),
    ANIMAL_SHELTER ("Animal Shelter"),
    FOOD_BANK ("Food Bank"),
    CHURCH_ORGANIZATION ("Church Organization"),
    PUBLIC_WORKS ("Public Works")
    ;

    private final String name;

    CategoryName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
