package ru.ivanishkin.javaee.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ivanishkin.javaee.task3.entity.WayEntity;
import ru.ivanishkin.javaee.task3.service.WayService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("way")
public class WayController {
    @Autowired
    private WayService wayService;

    @GetMapping(
            value = "get",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<WayEntity> getAllWays() {
        return wayService.getAllWays();
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Optional<WayEntity> getWay(@PathVariable("id") Long id) {
        return wayService.getWay(id);
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WayEntity createWay(@RequestBody WayEntity way) {
        return wayService.saveWay(way);
    }


    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteWay(@PathVariable("id") Long id) {
        wayService.deleteWay(id);
    }
}
