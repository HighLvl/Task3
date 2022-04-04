package task.dao;

import task.entity.TagEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagDao extends Dao<TagEntity> {
    public TagDao(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement() throws SQLException {
        return connection.prepareStatement(
                "INSERT INTO TAG(node_id, k, v) \n" +
                        "VALUES (?, ?, ?)");
    }

    @Override
    protected String buildInsertQuery(TagEntity entity) {
        return "INSERT INTO TAG(node_id, k, v) \n" +
                "VALUES (" + entity.getNodeId() + ", '" +
                entity.getK().replaceAll("'", "''") + "' , '" +
                entity.getV().replaceAll("'", "''") + "' )";
    }

    @Override
    protected void prepareInsertStatement(TagEntity entity) throws SQLException {
        insertPreparedStatement.setLong(1, entity.getNodeId());
        insertPreparedStatement.setString(2, entity.getK());
        insertPreparedStatement.setString(3, entity.getV());
    }
}
