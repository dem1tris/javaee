package ru.ivanishkin.javaee.task1.model;

public class UserChanges {
    String user;
    Long changes;

    public UserChanges(String user, Long changes) {
        this.user = user;
        this.changes = changes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getChanges() {
        return changes;
    }

    public void setChanges(Long changes) {
        this.changes = changes;
    }
}
