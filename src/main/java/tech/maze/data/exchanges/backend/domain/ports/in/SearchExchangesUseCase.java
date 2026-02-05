package tech.maze.data.exchanges.backend.domain.ports.in;

import java.util.List;
import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Use case for listing exchanges.
 */
public interface SearchExchangesUseCase {
  /**
   * Fetch all exchanges.
   *
   * @return list of exchanges
   */
  List<Exchange> findAll();
}
