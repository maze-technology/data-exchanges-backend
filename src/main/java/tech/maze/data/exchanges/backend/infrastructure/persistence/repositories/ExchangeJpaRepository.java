package tech.maze.data.exchanges.backend.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.maze.data.exchanges.backend.infrastructure.persistence.entities.ExchangeEntity;

/**
 * JPA repository for exchanges.
 */
@Repository
public interface ExchangeJpaRepository extends JpaRepository<ExchangeEntity, String> {}
