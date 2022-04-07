package ru.nsu.cherepanov.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsu.cherepanov.task.dto.RelationDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.service.RelationService;

import java.math.BigInteger;

@Controller
@RequestMapping(value = "/osm/relation")
public class RelationController {
    private final RelationService service;

    @Autowired
    public RelationController(RelationService service) {
        this.service = service;
    }

    @PostMapping(value = "create")
    public ResponseEntity<RelationDto> create(@RequestBody RelationDto dto) throws AlreadyExistsException {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<RelationDto> get(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<RelationDto> delete(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping(value = "update")
    public ResponseEntity<RelationDto> update(@RequestBody RelationDto nodeDto) throws NotFoundException {
        return ResponseEntity.ok(service.update(nodeDto));
    }
}
