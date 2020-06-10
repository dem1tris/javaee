package ru.ivanishkin.javaee.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ivanishkin.javaee.task3.entity.RelationEntity;
import ru.ivanishkin.javaee.task3.service.RelationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("relation")
public class RelationController {
    @Autowired
    private RelationService relationService;

    @GetMapping(
            value = "get",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<RelationEntity> getAllRelations() {
        return relationService.getAllRelations();
    }


    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Optional<RelationEntity> getRelation(@PathVariable("id") Long id) {

        return relationService.getRelation(id);

    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Long createRelation(@RequestBody RelationEntity relation) {

        return relationService.saveRelation(relation);
    }


    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteRelation(@PathVariable("id") Long id) {

        relationService.deleteRelation(id);
    }
}