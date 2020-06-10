package ru.ivanishkin.javaee.task3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ivanishkin.javaee.task3.entity.NodeEntity;
import ru.ivanishkin.javaee.task3.repository.NodeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    public List<NodeEntity> getAllNodes(int page, int limit) {
        return nodeRepository.findAll(PageRequest.of(page, limit)).getContent();
    }

    public NodeEntity getNode(Long id)  {
        Optional<NodeEntity> res = nodeRepository.findById(id);
        return res.orElse(null);
    }

    public Long saveNode(NodeEntity node) {
        NodeEntity newNode = nodeRepository.save(node);
        return newNode.id;
    }

    public void deleteNode(Long id) {
        nodeRepository.deleteById(id);
    }

    public List<NodeEntity> getNodesInRadius(Double lat, Double lon, Long radius) {
        return nodeRepository.getNodesInRadius(lat, lon, radius);
    }

}
