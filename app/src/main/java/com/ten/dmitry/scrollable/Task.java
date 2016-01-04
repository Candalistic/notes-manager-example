package com.ten.dmitry.scrollable;

public class Task {
    /*
        A class representation of a single task in the app.
     */
    private String key, name, description;
    public Task(String key, String name, String description){
        // A key must be unique for every task in the app!
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String toString(){
        return name;
    }

    public String getKey(){
        return key;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void setName(String new_name){
        name = new_name;
    }

    public void setDescription(String new_desc){
        description = new_desc;
    }
}
