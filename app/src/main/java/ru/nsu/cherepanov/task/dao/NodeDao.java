package ru.nsu.cherepanov.task.dao;

import ru.nsu.cherepanov.task.entity.NodeEntity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NodeDao extends Dao<NodeEntity> {
    public NodeDao(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement() throws SQLException {
        return connection.prepareStatement("INSERT INTO NODE(id, lat, lon, user_name, uid, version, changeset, timestamp, tags)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, hstore(?, ?))");
    }

    @Override
    protected String buildInsertQuery(NodeEntity entity) {
        return "INSERT INTO NODE(id, lat, lon, user_name, uid, version, changeset, timestamp) \n" +
                "VALUES (" + entity.getId() + ", " +
                entity.getLat() + ", " +
                entity.getLon() + ", '" +
                entity.getUserName().replaceAll("'", "''") + "' , " +
                entity.getUid() + ", " +
                entity.getVersion() + ", " +
                entity.getChangeset() + ", '" +
                entity.getTimestamp() + "' )";
    }

    @Override
    protected void prepareInsertStatement(NodeEntity entity) throws SQLException {
        insertPreparedStatement.setBigDecimal(1, new BigDecimal(entity.getId()));
        insertPreparedStatement.setDouble(2, entity.getLat());
        insertPreparedStatement.setDouble(3, entity.getLon());
        insertPreparedStatement.setString(4, entity.getUserName());
        insertPreparedStatement.setBigDecimal(5, new BigDecimal(entity.getUid()));
        insertPreparedStatement.setBigDecimal(6, new BigDecimal(entity.getVersion()));
        insertPreparedStatement.setBigDecimal(7, new BigDecimal(entity.getChangeset()));
        insertPreparedStatement.setTimestamp(8, entity.getTimestamp());
        insertPreparedStatement.setArray(9,
                connection.createArrayOf("text", entity.getTags().keySet().toArray()));
        insertPreparedStatement.setArray(10,
                connection.createArrayOf("text", entity.getTags().values().toArray()));

    }
}
