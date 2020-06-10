package ru.ivanishkin.javaee.task1.model;

public class UidMarks {
    String uid;
    Long marks;

    public UidMarks(String uid, Long marks) {
        this.uid = uid;
        this.marks = marks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getMarks() {
        return marks;
    }

    public void setMarks(Long marks) {
        this.marks = marks;
    }
}
