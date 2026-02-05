package tech.maze.data.exchanges.backend.domain.ports.in;

import java.util.Optional;
import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Use case for retrieving an exchange by id.
 */
public interface FindExchangeUseCase {
  /**
   * Find an exchange by its identifier.
   *
   * @param id exchange identifier
   * @return optional exchange
   */
  Optional<Exchange> findById(String id);
}
