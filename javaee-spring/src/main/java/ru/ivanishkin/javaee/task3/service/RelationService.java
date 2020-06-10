package ru.ivanishkin.javaee.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ivanishkin.javaee.task3.entity.RelationEntity;
import ru.ivanishkin.javaee.task3.repository.RelationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RelationService {

    @Autowired
    private RelationRepository relationRepository;

    public List<RelationEntity> getAllRelations() {
        return relationRepository.findAll();
    }

    public void deleteRelation(Long id) {
        relationRepository.deleteById(id);
    }

    public Long saveRelation(RelationEntity relation) {
        return relationRepository.save(relation).id;
    }

    public Optional<RelationEntity> getRelation(Long id) {
        return relationRepository.findById(id);
    }
}
