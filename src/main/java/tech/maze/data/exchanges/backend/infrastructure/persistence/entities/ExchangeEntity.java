package tech.maze.data.exchanges.backend.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity for exchanges.
 */
@Entity
@Table(name = "exchanges")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeEntity {
  @Id
  @Column(name = "id", nullable = false, length = 255)
  private String id;
}
