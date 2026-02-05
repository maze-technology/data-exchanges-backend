package tech.maze.data.exchanges.backend.domain.ports.in;

import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Use case for saving exchanges.
 */
public interface SaveExchangeUseCase {
  /**
   * Persist an exchange.
   *
   * @param exchange exchange to persist
   * @return saved exchange
   */
  Exchange save(Exchange exchange);
}
