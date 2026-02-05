package tech.maze.data.exchanges.backend.api.mappers;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import tech.maze.data.exchanges.backend.domain.models.Exchange;

/**
 * Maps exchange DTOs to domain models and back.
 */
@Component
public class ExchangeDtoMapper {
  /**
   * Maps a DTO exchange to the domain model.
   *
   * @param exchange DTO exchange
   * @return domain exchange
   */
  public Exchange toDomain(tech.maze.dtos.exchanges.models.Exchange exchange) {
    final Struct id = exchange.getId();
    return new Exchange(extractString(id).orElse(""));
  }

  /**
   * Maps a domain exchange to its DTO.
   *
   * @param exchange domain exchange
   * @return DTO exchange
   */
  public tech.maze.dtos.exchanges.models.Exchange toDto(Exchange exchange) {
    return tech.maze.dtos.exchanges.models.Exchange.newBuilder()
        .setId(toStruct(exchange.id()))
        .build();
  }

  private Struct toStruct(String value) {
    return Struct.newBuilder()
        .putFields("value", Value.newBuilder().setStringValue(value).build())
        .build();
  }

  private Optional<String> extractString(Struct struct) {
    if (struct == null) {
      return Optional.empty();
    }

    final Map<String, Value> fields = struct.getFieldsMap();
    if (fields.containsKey("value")) {
      final Value value = fields.get("value");
      if (value != null && value.hasStringValue()) {
        return Optional.of(value.getStringValue());
      }
    }

    return Optional.empty();
  }
}
