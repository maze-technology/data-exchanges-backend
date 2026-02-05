package tech.maze.data.exchanges.backend.domain.ports.out;

import java.util.Optional;
import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Port for loading an exchange from a persistence store.
 */
public interface LoadExchangePort {
  /**
   * Load an exchange by id.
   *
   * @param id exchange identifier
   * @return optional exchange
   */
  Optional<Exchange> findById(String id);
}
