package ru.nsu.cherepanov.task.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public class WayDto {
    private BigInteger id;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private List<BigInteger> refs;
    private List<TagDto> tags;

    public WayDto(BigInteger id, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, List<BigInteger> refs, List<TagDto> tags) {
        this.id = id;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.refs = refs;
        this.tags = tags;
    }

    public WayDto() {
    }

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

    public List<BigInteger> getRefs() {
        return refs;
    }

    public void setRefs(List<BigInteger> refs) {
        this.refs = refs;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}