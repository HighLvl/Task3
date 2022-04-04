package ru.nsu.cherepanov.task.entity;

import java.sql.Timestamp;

public class NodeEntity {
    private final int id;
    private final float lat;
    private final float lon;
    private final String userName;
    private final int uid;
    private final int version;
    private final int changeset;
    private final Timestamp timestamp;

    public NodeEntity(int id, float lat, float lon, String userName, int uid, int version, int changeset, Timestamp timestamp) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getUserName() {
        return userName;
    }

    public int getUid() {
        return uid;
    }

    public int getVersion() {
        return version;
    }

    public int getChangeset() {
        return changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
