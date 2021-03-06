package de.webtech.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Todo {

    @Id
    @GeneratedValue
    private Long        id;
    @NotNull(message = "A todo must have a subject.")
    private String      title;

    @NotNull(message="A todo has to be assigned to someone.")
    //@HasValidAssignee
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User      assignedUser;
    private boolean     done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "[TODO] " + id + " " + title + "[assigned to: " + assignedUser.getUsername() + " done?" + done + "]";
    }
}
