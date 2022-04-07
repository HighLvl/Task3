package ru.nsu.cherepanov.task.converter;

import ru.nsu.cherepanov.task.dto.*;
import ru.nsu.cherepanov.task.entity.Member;
import ru.nsu.cherepanov.task.entity.NodeEntity;
import ru.nsu.cherepanov.task.entity.RelationEntity;
import ru.nsu.cherepanov.task.entity.WayEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Converter {
    public static NodeDto toNodeDto(NodeEntity entity) {
        return new NodeDto(entity.getId(),
                (float) entity.getLat(),
                (float) entity.getLon(),
                entity.getUserName(),
                entity.getUid(),
                entity.getVersion(),
                entity.getChangeset(),
                entity.getTimestamp(),
                toTagDtoList(entity.getTags())
        );
    }

    public static NodeEntity toNodeEntity(NodeDto dto) {
        var entity = new NodeEntity();
        entity.setId(dto.getId());
        entity.setLat(dto.getLat());
        entity.setLon(dto.getLon());
        entity.setUserName(dto.getUserName());
        entity.setUid(dto.getUid());
        entity.setVersion(dto.getVersion());
        entity.setChangeset(dto.getChangeset());
        entity.setTimestamp(dto.getTimestamp());
        entity.setTags(toTagMap(dto.getTags()));
        return entity;
    }

    private static List<TagDto> toTagDtoList(Map<String, String> tags) {
        return tags.entrySet().stream().map((it) -> new TagDto(it.getKey(), it.getValue())).collect(Collectors.toList());
    }

    private static Map<String, String> toTagMap(List<TagDto> dto) {
        return dto.stream().collect(Collectors.toMap(TagDto::getK, TagDto::getV));
    }

    public static List<NodeDto> toNodeDtoList(List<NodeEntity> entities) {
        return entities.stream().map(Converter::toNodeDto).collect(Collectors.toList());
    }

    public static RelationEntity toRelationEntity(RelationDto dto) {
        return new RelationEntity(
                dto.getId(),
                dto.getUserName(),
                dto.getUid(),
                dto.getVersion(),
                dto.getChangeset(),
                dto.getTimestamp(),
                toTagMap(dto.getTags()),
                dto.getMembers().stream().map((it) -> new Member(it.getType(), it.getRef(), it.getRole())).collect(Collectors.toList())
        );
    }

    public static RelationDto toRelationDto(RelationEntity entity) {
        return new RelationDto(
                entity.getId(),
                entity.getUserName(),
                entity.getUid(),
                entity.getVersion(),
                entity.getChangeset(),
                entity.getTimestamp(),
                toTagDtoList(entity.getTags()),
                entity.getMembers().stream().map((it) -> new MemberDto(it.getType(), it.getRef(), it.getRole())).collect(Collectors.toList())

        );
    }

    public static WayEntity toWayEntity(WayDto dto) {
        return new WayEntity(
                dto.getId(),
                dto.getUserName(),
                dto.getUid(),
                dto.getVersion(),
                dto.getChangeset(),
                dto.getTimestamp(),
                dto.getRefs(),
                toTagMap(dto.getTags())
        );
    }

    public static WayDto toWayDto(WayEntity entity) {
        return new WayDto(
                entity.getId(),
                entity.getUserName(),
                entity.getUid(),
                entity.getVersion(),
                entity.getChangeset(),
                entity.getTimestamp(),
                entity.getRefs(),
                toTagDtoList(entity.getTags())
        );
    }
}
