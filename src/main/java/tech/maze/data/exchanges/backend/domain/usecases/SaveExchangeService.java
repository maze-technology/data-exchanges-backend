package tech.maze.data.exchanges.backend.domain.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.domain.ports.in.SaveExchangeUseCase;
import tech.maze.data.exchanges.backend.domain.ports.out.SaveExchangePort;

/**
 * Use case for saving exchanges.
 */
@Service
@RequiredArgsConstructor
public class SaveExchangeService implements SaveExchangeUseCase {
  private final SaveExchangePort saveExchangePort;

  @Override
  public Exchange save(Exchange exchange) {
    return saveExchangePort.save(exchange);
  }
}
