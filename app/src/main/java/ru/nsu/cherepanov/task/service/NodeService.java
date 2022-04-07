package ru.nsu.cherepanov.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.cherepanov.task.converter.Converter;
import ru.nsu.cherepanov.task.dto.NodeDto;
import ru.nsu.cherepanov.task.exception.AlreadyExistsException;
import ru.nsu.cherepanov.task.exception.NotFoundException;
import ru.nsu.cherepanov.task.repository.NodeEntityRepository;

import java.math.BigInteger;
import java.util.List;

@Service
public class NodeService {
    private final NodeEntityRepository repository;

    @Autowired
    public NodeService(NodeEntityRepository repository) {
        this.repository = repository;
    }

    public NodeDto create(NodeDto dto) throws AlreadyExistsException {
        if (repository.existsById(dto.getId())) {
            throw new AlreadyExistsException(dto.getId());
        }
        return Converter.toNodeDto(repository.save(Converter.toNodeEntity(dto)));
    }

    public NodeDto get(BigInteger id) throws NotFoundException {
        return Converter.toNodeDto(repository.findById(id).orElseThrow(() -> new NotFoundException(id)));
    }

    public NodeDto update(NodeDto dto) throws NotFoundException {
        if (!repository.existsById(dto.getId())) {
            throw new NotFoundException(dto.getId());
        }
        repository.deleteById(dto.getId());
        return Converter.toNodeDto(repository.save(Converter.toNodeEntity(dto)));
    }

    public NodeDto delete(BigInteger id) throws NotFoundException {
        var node = repository.findById(id).orElseThrow(() -> new NotFoundException(id));
        repository.deleteById(id);
        return Converter.toNodeDto(node);
    }

    public List<NodeDto> getNodesByRadius(Double lon, Double lat, Double radius) {
        return Converter.toNodeDtoList(repository.getNodesByRadius(lon, lat, radius));
    }
}
