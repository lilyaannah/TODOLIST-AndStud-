package com.example.todolist.model;

public class ToDoModel {

    private String task;
    private String description;
    private int id , status;

    public String getDescription(){ return description; }
    public void setDescription (String description){
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
