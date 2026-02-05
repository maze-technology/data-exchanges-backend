package tech.maze.data.exchanges.backend.domain.usecases;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.domain.ports.in.FindExchangeUseCase;
import tech.maze.data.exchanges.backend.domain.ports.out.LoadExchangePort;

/**
 * Use case for loading an exchange by id.
 */
@Service
@RequiredArgsConstructor
public class FindExchangeService implements FindExchangeUseCase {
  private final LoadExchangePort loadExchangePort;

  @Override
  public Optional<Exchange> findById(String id) {
    return loadExchangePort.findById(id);
  }
}
