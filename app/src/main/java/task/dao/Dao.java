package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao<T> {
    protected final PreparedStatement insertPreparedStatement;
    protected final Connection connection;

    public Dao(Connection connection) throws SQLException {
        this.connection = connection;
        insertPreparedStatement = createInsertPreparedStatement();
    }

    protected abstract PreparedStatement createInsertPreparedStatement() throws SQLException;

    protected abstract String buildInsertQuery(T entity);

    final public void insert(T entity) throws SQLException {
        var query = buildInsertQuery(entity);
        var statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    final public void insertWithPreparedStatement(T entity) throws SQLException {
        prepareInsertStatement(entity);
        insertPreparedStatement.executeUpdate();
    }

    protected abstract void prepareInsertStatement(T entity) throws SQLException;

    final public void insertBatch(List<T> entity) throws SQLException {
        for (T it : entity) {
            prepareInsertStatement(it);
            insertPreparedStatement.addBatch();
        }
        insertPreparedStatement.executeBatch();
    }
}
