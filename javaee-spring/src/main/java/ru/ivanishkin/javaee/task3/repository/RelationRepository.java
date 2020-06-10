package ru.ivanishkin.javaee.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanishkin.javaee.task3.entity.RelationEntity;

@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, Long> {
}