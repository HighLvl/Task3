package ru.nsu.cherepanov.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.cherepanov.task.converter.Converter;
import ru.nsu.cherepanov.task.dto.WayDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.repository.WayEntityRepository;

import java.math.BigInteger;

@Service
public class WayService {
    private final WayEntityRepository repository;

    @Autowired
    public WayService(WayEntityRepository relationRepository) {
        this.repository = relationRepository;
    }

    public WayDto create(WayDto dto) throws AlreadyExistsException {
        if (repository.existsById(dto.getId())) {
            throw new AlreadyExistsException(dto.getId());
        }
        return Converter.toWayDto(repository.save(Converter.toWayEntity(dto)));
    }

    public WayDto get(BigInteger id) throws NotFoundException {
        return Converter.toWayDto(repository.findById(id).orElseThrow(() -> new NotFoundException(id)));
    }

    public WayDto update(WayDto dto) throws NotFoundException {
        if (!repository.existsById(dto.getId())) {
            throw new NotFoundException(dto.getId());
        }
        repository.deleteById(dto.getId());
        return Converter.toWayDto(repository.save(Converter.toWayEntity(dto)));
    }

    public WayDto delete(BigInteger id) throws NotFoundException {
        var node = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        repository.deleteById(id);
        return Converter.toWayDto(node);
    }

}
