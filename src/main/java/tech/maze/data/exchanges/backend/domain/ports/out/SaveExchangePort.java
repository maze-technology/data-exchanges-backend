package tech.maze.data.exchanges.backend.domain.ports.out;

import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Port for saving exchanges in a persistence store.
 */
public interface SaveExchangePort {
  /**
   * Persist an exchange.
   *
   * @param exchange exchange to persist
   * @return saved exchange
   */
  Exchange save(Exchange exchange);
}
