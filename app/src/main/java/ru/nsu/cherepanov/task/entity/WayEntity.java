package ru.nsu.cherepanov.task.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "way", schema = "public", catalog = "osm")
public class WayEntity {
    private BigInteger id;
    private String userName;
    private BigInteger uid;
    private BigInteger version;
    private BigInteger changeset;
    private Timestamp timestamp;
    private List<BigInteger> refs;
    private Map<String, String> tags;

    public WayEntity(BigInteger id, String userName, BigInteger uid, BigInteger version, BigInteger changeset, Timestamp timestamp, List<BigInteger> refs, Map<String, String> tags) {
        this.id = id;
        this.userName = userName;
        this.uid = uid;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
        this.refs = refs;
        this.tags = tags;
    }

    public WayEntity() {
    }

    @Convert(converter = RefsConverter.class)
    @Column(
            name = "refs", columnDefinition = "bigint[]"
    )
    public List<BigInteger> getRefs() {
        return refs;
    }

    public void setRefs(List<BigInteger> refs) {
        this.refs = refs;
    }

    @Convert(converter = MyHStoreConverter.class)
    @Column(name = "tags")
    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
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
        WayEntity wayEntity = (WayEntity) o;
        return id.equals(wayEntity.id) && userName.equals(wayEntity.userName) && uid.equals(wayEntity.uid) && version.equals(wayEntity.version) && changeset.equals(wayEntity.changeset) && timestamp.equals(wayEntity.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, uid, version, changeset, timestamp);
    }

    @Override
    public String toString() {
        return "WayEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", uid=" + uid +
                ", version=" + version +
                ", changeset=" + changeset +
                ", timestamp=" + timestamp +
                ", refs=" + refs +
                ", tags=" + tags +
                '}';
    }
}
