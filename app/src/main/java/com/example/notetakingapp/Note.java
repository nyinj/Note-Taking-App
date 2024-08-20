package com.example.notetakingapp;

public class Note {

    private long id;
    private String title;
    private String description;
    private long createdTime;

    // Constructor
    public Note() {
    }

    public Note(long id, String title, String description, long createdTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdTime = createdTime;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
