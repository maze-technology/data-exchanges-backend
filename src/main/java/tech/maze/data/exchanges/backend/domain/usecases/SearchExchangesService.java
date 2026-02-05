package tech.maze.data.exchanges.backend.domain.usecases;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.domain.ports.in.SearchExchangesUseCase;
import tech.maze.data.exchanges.backend.domain.ports.out.SearchExchangesPort;

/**
 * Use case for listing exchanges.
 */
@Service
@RequiredArgsConstructor
public class SearchExchangesService implements SearchExchangesUseCase {
  private final SearchExchangesPort searchExchangesPort;

  @Override
  public List<Exchange> findAll() {
    return searchExchangesPort.findAll();
  }
}
