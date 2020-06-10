package ru.ivanishkin.javaee.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ivanishkin.javaee.task3.entity.TagEntity;
import ru.ivanishkin.javaee.task3.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping(
            value = "get",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<TagEntity> getAllTags() {
        return tagService.getAllTags();

    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Optional<TagEntity> getTag(@PathVariable("id") Long id) {

        return tagService.getTag(id);
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Long createTag(@RequestBody TagEntity tag) {
        return tagService.saveTag(tag);
    }


    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
    }
}
