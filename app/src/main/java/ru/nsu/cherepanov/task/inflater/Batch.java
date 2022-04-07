package ru.nsu.cherepanov.task.inflater;

import ru.nsu.cherepanov.task.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Batch <T> {
    private final int capacity;
    private final List<T> batch;
    private final Dao<T> dao;

    public Batch(int capacity, Dao<T> dao) {
        this.capacity = capacity;
        this.batch = new ArrayList<>(capacity);
        this.dao = dao;
    }

    public void insert(T entity) throws SQLException {
        batch.add(entity);
        if (batch.size() == capacity) {
            flush();
        }
    }

    public void flush() throws SQLException {
        if (batch.isEmpty()) return;
        dao.insertBatch(batch);
        batch.clear();
    }
}
