package ru.ivanishkin.javaee.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ivanishkin.javaee.task3.entity.TagEntity;
import ru.ivanishkin.javaee.task3.repository.TagRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<TagEntity> getTag(Long id) {
        return tagRepository.findById(id);
    }

    public Long saveTag(TagEntity tag) {
        return tagRepository.save(tag).id;
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}