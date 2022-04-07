package ru.nsu.cherepanov.task.inflater;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.cherepanov.osm.model.generated.*;
import ru.nsu.cherepanov.task.osm.OsmHandler;
import ru.nsu.cherepanov.task.dao.Dao;
import ru.nsu.cherepanov.task.entity.Member;
import ru.nsu.cherepanov.task.entity.NodeEntity;
import ru.nsu.cherepanov.task.entity.RelationEntity;
import ru.nsu.cherepanov.task.entity.WayEntity;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbInflater implements OsmHandler {
    private static final int BATCH_SIZE = 1000000;

    private final InsertType insertType;
    private final Dao<NodeEntity> nodeDao;
    private long totalTime = 0L;
    private int nodesNumber = 0;
    private final Logger logger = LogManager.getLogger(DbInflater.class);
    private final Batch<NodeEntity> nodeBatch;
    private final Batch<RelationEntity> relationBatch;
    private final Batch<WayEntity> wayBatch;

    public DbInflater(Dao<NodeEntity> nodeDao, Dao<RelationEntity> relationDao, Dao<WayEntity> wayDao, InsertType insertType) {
        this.nodeDao = nodeDao;
        this.insertType = insertType;
        nodeBatch = new Batch<>(BATCH_SIZE, nodeDao);
        relationBatch = new Batch<>(BATCH_SIZE, relationDao);
        wayBatch = new Batch<>(BATCH_SIZE, wayDao);
    }

    public float getNodesPerSecond() {
        return (float) (nodesNumber / (totalTime / 1e9));
    }

    @Override
    public void handleNode(Node node) {
        var nodeEntity = mapToEntity(node);
        insertAndMeasureTime(nodeEntity);
    }

    private void insertAndMeasureTime(NodeEntity nodeEntity) {
        var startTime = System.nanoTime();
        try {
            tryInsert(nodeEntity);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        var finishTime = System.nanoTime();
        totalTime += finishTime - startTime;
        nodesNumber++;
    }

    private void tryInsert(NodeEntity nodeEntity) throws SQLException {
        switch (insertType) {
            case SIMPLE -> insert(nodeEntity);
            case PREPARED_STATEMENT -> insertWithPreparedStatement(nodeEntity);
            case BATCH -> insertBatch(nodeEntity);
        }
    }

    private void insert(NodeEntity nodeEntity) throws SQLException {
        nodeDao.insert(nodeEntity);
    }

    private void insertWithPreparedStatement(NodeEntity nodeEntity) throws SQLException {
        nodeDao.insertWithPreparedStatement(nodeEntity);
    }

    private void insertBatch(NodeEntity nodeEntity) throws SQLException {
        nodeBatch.insert(nodeEntity);
    }

    private static NodeEntity mapToEntity(Node node) {
        return new NodeEntity(
                node.getId(),
                node.getLat().floatValue(),
                node.getLon().floatValue(),
                node.getUser(),
                node.getVersion(),
                node.getUid(),
                node.getChangeset(),
                toTimestamp(node.getTimestamp()),
                toMap(node.getTag())
        );
    }

    private static Map<String, String> toMap(List<Tag> tags) {
        return tags.stream().collect(Collectors.toMap(Tag::getK, Tag::getV));
    }

    private static Timestamp toTimestamp(XMLGregorianCalendar calendar) {
        return Timestamp.from(calendar.toGregorianCalendar().getTime().toInstant());
    }

    @Override
    public void handleRelation(Relation relation) {
        try {
            relationBatch.insert(mapToEntity(relation));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static RelationEntity mapToEntity(Relation relation) {
        return new RelationEntity(
                relation.getId(),
                relation.getUser(),
                relation.getUid(),
                relation.getVersion(),
                relation.getChangeset(),
                toTimestamp(relation.getTimestamp()),
                toMap(relation.getTag()),
                relation.getMember().stream().map((it) -> new Member(it.getType(), it.getRef(), it.getRole())).collect(Collectors.toList())
        );
    }

    @Override
    public void handleWay(Way way) {
        try {
            wayBatch.insert(mapToEntity(way));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static WayEntity mapToEntity(Way way) {
        return new WayEntity(
                way.getId(),
                way.getUser(),
                way.getUid(),
                way.getVersion(),
                way.getChangeset(),
                toTimestamp(way.getTimestamp()),
                way.getNd().stream().map(Nd::getRef).collect(Collectors.toList()),
                toMap(way.getTag()));
    }

    @Override
    public void finish() {
        try {
            nodeBatch.flush();
            relationBatch.flush();
            wayBatch.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public enum InsertType {
        SIMPLE, PREPARED_STATEMENT, BATCH;

        public static InsertType of(int type) {
            return switch (type) {
                case 0 -> SIMPLE;
                case 1 -> PREPARED_STATEMENT;
                case 2 -> BATCH;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
