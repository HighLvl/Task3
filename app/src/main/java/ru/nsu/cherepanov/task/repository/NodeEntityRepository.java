package ru.nsu.cherepanov.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.cherepanov.task.entity.NodeEntity;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NodeEntityRepository extends JpaRepository<NodeEntity, BigInteger> {
    @Query(value = "select * from node " +
            "where 6371000 * 2 * ASIN(" +
            "sqrt(power(SIN((node.lat - ABS(?2)) * PI() / 180 / 2), 2) + " +
            "cos(node.lat * PI() / 180) * cos(?2 * PI() / 180) * power(SIN((node.lon - ?1) * PI() / 180 / 2), 2))) < ?3", nativeQuery = true)
    List<NodeEntity> getNodesByRadius(double lon, double lat, double radius);
}