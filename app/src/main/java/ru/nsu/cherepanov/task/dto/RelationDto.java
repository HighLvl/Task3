package ru.nsu.cherepanov.task.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public class RelationDto {
    private BigInteger id;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private List<TagDto> tags;
    private List<MemberDto> members;

    public RelationDto(BigInteger id, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, List<TagDto> tags, List<MemberDto> members) {
        this.id = id;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.tags = tags;
        this.members = members;
    }

    public  RelationDto() { }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<MemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDto> members) {
        this.members = members;
    }
}
