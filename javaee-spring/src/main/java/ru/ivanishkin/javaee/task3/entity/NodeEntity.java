package ru.ivanishkin.javaee.task3.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nodes")
public class NodeEntity {

    public NodeEntity() {
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public Double lon;
    public Double lat;
    public String userName;
    public Long uid;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "nodeId")
    public List<TagEntity> tags = new ArrayList<>();
}