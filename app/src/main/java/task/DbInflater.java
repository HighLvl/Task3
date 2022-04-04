package task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import osm.model.generated.Node;
import osm.model.generated.Tag;
import task.dao.Dao;
import task.entity.NodeEntity;
import task.entity.TagEntity;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbInflater implements OsmHandler {
    private static final int BATCH_SIZE = 1000000;

    private final Dao<NodeEntity> nodeDao;
    private final Dao<TagEntity> tagDao;
    private final InsertType insertType;
    private long totalTime = 0L;
    private int nodesNumber = 0;
    private final Logger logger = LogManager.getLogger(DbInflater.class);
    private final List<NodeEntity> nodeBatch = new ArrayList<>(BATCH_SIZE);
    private final List<TagEntity> tagBatch = new ArrayList<>(BATCH_SIZE * 2);

    public DbInflater(Dao<NodeEntity> nodeDao, Dao<TagEntity> tagDao, InsertType insertType) {
        this.nodeDao = nodeDao;
        this.tagDao = tagDao;
        this.insertType = insertType;
    }

    public float getNodesPerSecond() {
        return (float) (nodesNumber / (totalTime / 1e9));
    }

    @Override
    public void handleNode(Node node) {
        var nodeEntity = mapToEntity(node);
        var nodeId = nodeEntity.getId();
        var tagEntities = node.getTag().stream()
                .map((tag -> mapToEntity(nodeId, tag)))
                .collect(Collectors.toList());
        insertAndMeasureTime(nodeEntity, tagEntities);
    }

    private void insertAndMeasureTime(NodeEntity nodeEntity, List<TagEntity> tagEntities) {
        var startTime = System.nanoTime();
        try {
            tryInsert(nodeEntity, tagEntities);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        var finishTime = System.nanoTime();
        totalTime += finishTime - startTime;
        nodesNumber++;
    }

    private void tryInsert(NodeEntity nodeEntity, List<TagEntity> tagEntities) throws SQLException {
        switch (insertType) {
            case SIMPLE -> insert(nodeEntity, tagEntities);
            case PREPARED_STATEMENT -> insertWithPreparedStatement(nodeEntity, tagEntities);
            case BATCH -> insertBatch(nodeEntity, tagEntities);
        }
    }

    private void insert(NodeEntity nodeEntity, List<TagEntity> tagEntities) throws SQLException {
        nodeDao.insert(nodeEntity);
        for (TagEntity tagEntity : tagEntities) {
            tagDao.insert(tagEntity);
        }
    }

    private void insertWithPreparedStatement(NodeEntity nodeEntity, List<TagEntity> tagEntities) throws SQLException {
        nodeDao.insertWithPreparedStatement(nodeEntity);
        for (TagEntity tagEntity : tagEntities) {
            tagDao.insertWithPreparedStatement(tagEntity);
        }
    }

    private void insertBatch(NodeEntity nodeEntity, List<TagEntity> tagEntities) throws SQLException {
        if (nodeBatch.size() == BATCH_SIZE) {
            nodeDao.insertBatch(nodeBatch);
            nodeBatch.clear();

            tagDao.insertBatch(tagBatch);
            tagBatch.clear();
        } else {
            nodeBatch.add(nodeEntity);
            tagBatch.addAll(tagEntities);
        }
    }

    private static NodeEntity mapToEntity(Node node) {
        return new NodeEntity(
                node.getId().intValue(),
                node.getLat().floatValue(),
                node.getLon().floatValue(),
                node.getUser(),
                node.getVersion().intValue(),
                node.getUid().intValue(),
                node.getChangeset().intValue(),
                Timestamp.from(node.getTimestamp().toGregorianCalendar().getTime().toInstant())
        );
    }

    private static TagEntity mapToEntity(int nodeId, Tag tag) {
        return new TagEntity(
                nodeId,
                tag.getK(),
                tag.getV()
        );
    }

    enum InsertType {
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
