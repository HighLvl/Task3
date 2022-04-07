package ru.nsu.cherepanov.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsu.cherepanov.task.dto.WayDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.service.WayService;

import java.math.BigInteger;

@Controller
@RequestMapping(value = "/osm/way")
public class WayController {
    private final WayService service;

    @Autowired
    public WayController(WayService service) {
        this.service = service;
    }

    @PostMapping(value = "create")
    public ResponseEntity<WayDto> create(@RequestBody WayDto dto) throws AlreadyExistsException {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<WayDto> get(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<WayDto> delete(@PathVariable("id") BigInteger id) throws NotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping(value = "update")
    public ResponseEntity<WayDto> update(@RequestBody WayDto nodeDto) throws NotFoundException {
        return ResponseEntity.ok(service.update(nodeDto));
    }
}
