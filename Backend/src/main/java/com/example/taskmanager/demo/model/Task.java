package com.example.taskmanager.demo.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Entity
@Table(name = "TASKS")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Title must not be empty")
    @NotNull(message = "Title must not be empty")
    private String title;
    private String description;
    @NotNull(message = "Status must not be null")
    @Pattern(regexp = "TO DO|IN PROGRESS|DONE", message = "Status must be one of TO DO, IN PROGRESS, DONE")
    private String status;
    private Date due_date;
    @NotNull(message = "Priority must not be null")
    @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be one of LOW, MEDIUM, HIGH")
    private String priority;

    public Task() {
    }

    public Task(String title, String description, String status, Date dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.due_date = dueDate;
        this.priority = priority;
    }
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Date getDueDate() {
        return due_date;
    }

    public String getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(Date dueDate) {
        this.due_date = dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
