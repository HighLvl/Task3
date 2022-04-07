package ru.nsu.cherepanov.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsu.cherepanov.task.dto.NodeDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.service.NodeService;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping(value = "/osm/node")
public class NodeController {
    private final NodeService service;

    @Autowired
    public NodeController(NodeService service) {
        this.service = service;
    }

    @PostMapping(value = "create")
    public ResponseEntity<NodeDto> createNode(@RequestBody NodeDto dto) throws AlreadyExistsException {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<NodeDto> getNode(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<NodeDto> deleteNode(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping(value = "update")
    public ResponseEntity<NodeDto> updateNode(@RequestBody NodeDto nodeDto) throws NotFoundException {
        return ResponseEntity.ok(service.update(nodeDto));
    }

    @GetMapping(value = "nodesByRadius")
    public ResponseEntity<List<NodeDto>> getNodesByRadius(@RequestParam("lon") Double lon, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        return ResponseEntity.ok(service.getNodesByRadius(lon, lat, radius));
    }
}
