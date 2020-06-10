package ru.ivanishkin.javaee.task3.entity;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String key;
    public String value;
    @Column
    public Long nodeId;
}