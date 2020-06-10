package ru.ivanishkin.javaee.task3.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.ivanishkin.javaee.task3.entity.NodeEntity;

import java.util.List;

@Repository
public interface NodeRepository extends PagingAndSortingRepository<NodeEntity, Long> {

    @Query(value = "SELECT * " +
            "FROM nodes " +
            "WHERE earth_box(ll_to_earth(?1,?2), ?3) " +
            "@> " +
            "ll_to_earth(lat, lon) ORDER by earth_distance(ll_to_earth(?1,?2), ll_to_earth(lat, lon));",
            nativeQuery = true
    )
    List<NodeEntity> getNodesInRadius(Double lat, Double lon, Long radius);
}
