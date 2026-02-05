package tech.maze.data.exchanges.backend.infrastructure.persistence.mappers;

import org.mapstruct.Mapper;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.infrastructure.persistence.entities.ExchangeEntity;

/**
 * Maps exchange entities to domain models and back.
 */
@Mapper(componentModel = "spring")
public interface ExchangeEntityMapper {
  /**
   * Map a persistence entity to the domain model.
   *
   * @param entity persistence entity
   * @return domain model
   */
  Exchange toDomain(ExchangeEntity entity);

  /**
   * Map a domain model to its persistence entity.
   *
   * @param exchange domain model
   * @return persistence entity
   */
  ExchangeEntity toEntity(Exchange exchange);
}
