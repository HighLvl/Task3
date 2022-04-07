package ru.nsu.cherepanov.task.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "relation", schema = "public", catalog = "osm")
public class RelationEntity {
    private BigInteger id;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private Map<String, String> tags;
    private List<Member> members;

    public RelationEntity(BigInteger id, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, Map<String, String> tags, List<Member> members) {
        this.id = id;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.tags = tags;
        this.members = members;
    }

    public RelationEntity() { }

    @Convert(converter = MyHStoreConverter.class)
    @Column(name = "tags")
    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    @Type(type = "ru.nsu.cherepanov.task.entity.MembersType")
    @Column(name = "members", columnDefinition = "member[]")
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Id
    @Column(name = "id")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "uid")
    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "version")
    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @Basic
    @Column(name = "changeset")
    public BigInteger getChangeset() {
        return changeset;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    @Basic
    @Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationEntity that = (RelationEntity) o;
        return id.equals(that.id) && userName.equals(that.userName) && uid.equals(that.uid) && version.equals(that.version) && changeset.equals(that.changeset) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, uid, version, changeset, timestamp);
    }

    @Override
    public String toString() {
        return "RelationEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", uid=" + uid +
                ", version=" + version +
                ", changeset=" + changeset +
                ", timestamp=" + timestamp +
                ", tags=" + tags +
                ", members=" + members +
                '}';
    }
}
