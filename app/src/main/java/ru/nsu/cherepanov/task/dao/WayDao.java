package ru.nsu.cherepanov.task.dao;

import ru.nsu.cherepanov.task.entity.WayEntity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WayDao extends Dao<WayEntity> {
    public WayDao(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement() throws SQLException {
        return connection.prepareStatement("INSERT INTO WAY(id, user_name, uid, version, changeset, timestamp, refs, tags)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, hstore(?, ?))");
    }

    @Override
    protected String buildInsertQuery(WayEntity entity) {
        return null;
    }

    @Override
    protected void prepareInsertStatement(WayEntity entity) throws SQLException {
        insertPreparedStatement.setBigDecimal(1, new BigDecimal(entity.getId()));
        insertPreparedStatement.setString(2, entity.getUserName());
        insertPreparedStatement.setBigDecimal(3, new BigDecimal(entity.getUid()));
        insertPreparedStatement.setBigDecimal(4, new BigDecimal(entity.getVersion()));
        insertPreparedStatement.setBigDecimal(5, new BigDecimal(entity.getChangeset()));
        insertPreparedStatement.setTimestamp(6, entity.getTimestamp());
        insertPreparedStatement.setArray(7,
                connection.createArrayOf("bigint", entity.getRefs().toArray()));
        insertPreparedStatement.setArray(8,
                connection.createArrayOf("text", entity.getTags().keySet().toArray()));
        insertPreparedStatement.setArray(9,
                connection.createArrayOf("text", entity.getTags().values().toArray()));
    }
}
