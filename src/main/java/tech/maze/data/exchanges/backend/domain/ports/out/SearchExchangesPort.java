package tech.maze.data.exchanges.backend.domain.ports.out;

import java.util.List;
import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Port for listing exchanges from a persistence store.
 */
public interface SearchExchangesPort {
  /**
   * Load all exchanges.
   *
   * @return list of exchanges
   */
  List<Exchange> findAll();
}
