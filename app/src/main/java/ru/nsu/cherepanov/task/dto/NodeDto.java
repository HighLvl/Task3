package ru.nsu.cherepanov.task.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public class NodeDto {
    private BigInteger id;
    private double lat;
    private double lon;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private List<TagDto> tags;

    public NodeDto(BigInteger id, double lat, double lon, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, List<TagDto> tags) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.tags = tags;
    }

    public NodeDto() {
    }

    public BigInteger getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getUserName() {
        return userName;
    }

    public BigInteger getUid() {
        return uid;
    }

    public BigInteger getVersion() {
        return version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
