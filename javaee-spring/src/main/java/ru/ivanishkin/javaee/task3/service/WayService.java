package ru.ivanishkin.javaee.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ivanishkin.javaee.task3.entity.WayEntity;
import ru.ivanishkin.javaee.task3.repository.WayRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WayService {

    @Autowired
    private WayRepository wayRepository;

    public List<WayEntity> getAllWays() {
        return wayRepository.findAll();
    }

    public Optional<WayEntity> getWay(Long id) {
        return wayRepository.findById(id);
    }

    public WayEntity saveWay(WayEntity way) {
        return wayRepository.save(way);
    }

    public void deleteWay(Long id) {
        wayRepository.deleteById(id);
    }
}