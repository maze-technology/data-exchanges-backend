package tech.maze.data.exchanges.backend.domain.models;

import java.util.Objects;

/**
 * Domain model for an exchange.
 *
 * @param id unique exchange identifier
 */
public record Exchange(String id) {
  public Exchange {
    Objects.requireNonNull(id, "id must not be null");
  }
}
