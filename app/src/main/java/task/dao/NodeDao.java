package task.dao;

import task.entity.NodeEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NodeDao extends Dao<NodeEntity> {
    public NodeDao(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected PreparedStatement createInsertPreparedStatement() throws SQLException {
        return connection.prepareStatement("INSERT INTO NODE(id, lat, lon, user_name, uid, version, changeset, timestamp)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
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
        insertPreparedStatement.setLong(1, entity.getId());
        insertPreparedStatement.setDouble(2, entity.getLat());
        insertPreparedStatement.setDouble(3, entity.getLon());
        insertPreparedStatement.setString(4, entity.getUserName());
        insertPreparedStatement.setLong(5, entity.getUid());
        insertPreparedStatement.setInt(6, entity.getVersion());
        insertPreparedStatement.setLong(7, entity.getChangeset());
        insertPreparedStatement.setTimestamp(8, entity.getTimestamp());
    }
}
