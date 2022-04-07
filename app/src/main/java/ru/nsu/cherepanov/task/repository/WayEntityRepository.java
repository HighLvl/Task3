package ru.nsu.cherepanov.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.cherepanov.task.entity.WayEntity;

import java.math.BigInteger;

@Repository
public interface WayEntityRepository extends JpaRepository<WayEntity, BigInteger> {
}
