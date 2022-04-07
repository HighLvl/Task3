package ru.nsu.cherepanov.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.cherepanov.task.converter.Converter;
import ru.nsu.cherepanov.task.dto.RelationDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.repository.RelationEntityRepository;

import java.math.BigInteger;

@Service
public class RelationService {
    private final RelationEntityRepository repository;

    @Autowired
    public RelationService(RelationEntityRepository relationRepository) {
        this.repository = relationRepository;
    }

    public RelationDto create(RelationDto dto) throws AlreadyExistsException {
        if (repository.existsById(dto.getId())) {
            throw new AlreadyExistsException(dto.getId());
        }
        return Converter.toRelationDto(repository.save(Converter.toRelationEntity(dto)));
    }

    public RelationDto get(BigInteger id) throws NotFoundException {
        return Converter.toRelationDto(repository.findById(id).orElseThrow(() -> new NotFoundException(id)));
    }

    public RelationDto update(RelationDto dto) throws NotFoundException {
        if (!repository.existsById(dto.getId())) {
            throw new NotFoundException(dto.getId());
        }
        repository.deleteById(dto.getId());
        return Converter.toRelationDto(repository.save(Converter.toRelationEntity(dto)));
    }

    public RelationDto delete(BigInteger id) throws NotFoundException {
        var node = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        repository.deleteById(id);
        return Converter.toRelationDto(node);
    }

}
