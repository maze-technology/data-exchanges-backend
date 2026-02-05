package tech.maze.data.exchanges.backend.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.domain.ports.out.LoadExchangePort;
import tech.maze.data.exchanges.backend.domain.ports.out.SaveExchangePort;
import tech.maze.data.exchanges.backend.domain.ports.out.SearchExchangesPort;
import tech.maze.data.exchanges.backend.infrastructure.persistence.entities.ExchangeEntity;
import tech.maze.data.exchanges.backend.infrastructure.persistence.mappers.ExchangeEntityMapper;
import tech.maze.data.exchanges.backend.infrastructure.persistence.repositories.ExchangeJpaRepository;

/**
 * Persistence adapter for exchanges.
 */
@Component
@RequiredArgsConstructor
public class ExchangePersistenceAdapter implements LoadExchangePort,
    SaveExchangePort,
    SearchExchangesPort {
  private final ExchangeJpaRepository exchangeJpaRepository;
  private final ExchangeEntityMapper exchangeEntityMapper;

  @Override
  public Optional<Exchange> findById(String id) {
    return exchangeJpaRepository.findById(id).map(exchangeEntityMapper::toDomain);
  }

  @Override
  public List<Exchange> findAll() {
    return exchangeJpaRepository.findAll().stream().map(exchangeEntityMapper::toDomain).toList();
  }

  @Override
  public Exchange save(Exchange exchange) {
    final ExchangeEntity entity = exchangeEntityMapper.toEntity(exchange);
    return exchangeEntityMapper.toDomain(exchangeJpaRepository.save(entity));
  }
}
