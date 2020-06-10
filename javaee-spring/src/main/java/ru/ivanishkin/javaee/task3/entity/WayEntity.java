package ru.ivanishkin.javaee.task3.entity;

import javax.persistence.*;

@Entity
@Table(name = "ways")
public class WayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String userName;
    public Long uid;
    public Boolean visible;
    public Long version;
    public Long changeset;
}