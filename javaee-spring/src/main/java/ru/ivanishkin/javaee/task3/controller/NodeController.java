package ru.ivanishkin.javaee.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ivanishkin.javaee.task3.entity.NodeEntity;
import ru.ivanishkin.javaee.task3.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("node")
class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping(
            value = "radius",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<NodeEntity> getNodesInRadius(
            @RequestParam("lon") Double lon,
            @RequestParam("lat") Double lat,
            @RequestParam("radius") Long radius
    ) {
        return nodeService.getNodesInRadius(lat, lon, radius);
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<NodeEntity> getAllNodes(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "limit", required = false) Integer limit) {
        return nodeService.getAllNodes(page != null ? page : 0, limit != null ? limit : 50);
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public NodeEntity getNode(@PathVariable("id") Long id) {

        return nodeService.getNode(id);
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Long createNode(@RequestBody NodeEntity node) {

        return nodeService.saveNode(node);
    }


    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteNode(@PathVariable("id") Long id) {
        nodeService.deleteNode(id);
    }
}