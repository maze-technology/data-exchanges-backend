package tech.maze.data.exchanges.backend.api.eventstream;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.maze.commons.eventstream.EventSender;
import tech.maze.commons.eventstream.MazeEventProperties;
import tech.maze.data.exchanges.backend.api.mappers.ExchangeDtoMapper;
import tech.maze.data.exchanges.backend.domain.models.Exchange;
import tech.maze.data.exchanges.backend.domain.ports.in.FindExchangeUseCase;
import tech.maze.dtos.exchanges.requests.FindOneRequest;
import tech.maze.dtos.exchanges.requests.FindOneResponse;
import tech.maze.dtos.exchanges.search.Criterion;
import tech.maze.dtos.exchanges.search.CriterionFilter;

/**
 * Event stream configuration for exchanges processing.
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableConfigurationProperties(MazeEventProperties.class)
@Slf4j
public class ExchangesEventStreamConfiguration {
  EventSender eventSender;
  FindExchangeUseCase findExchangeUseCase;
  ExchangeDtoMapper exchangeDtoMapper;

  /**
   * Handles exchange events delivered via the event stream.
   *
   * @return a consumer for CloudEvents
   */
  @Bean
  public Consumer<CloudEvent> exchangesConsumer() {
    return event -> {
      if (!ExchangeEventTypes.FIND_ONE_REQUEST.equals(event.getType())) {
        log.warn(
            "Skipping event type {} (expected {})",
            event.getType(),
            ExchangeEventTypes.FIND_ONE_REQUEST
        );
        return;
      }

      final FindOneRequest request = parseFindOne(event);
      final Optional<String> exchangeId = resolveExchangeId(request);
      if (exchangeId.isEmpty()) {
        log.warn("FindOneRequest missing an exchange identifier");
        sendReply(event, FindOneResponse.newBuilder().build());
        return;
      }

      final Optional<Exchange> exchange = findExchangeUseCase.findById(exchangeId.get());
      final FindOneResponse response = exchange
          .map(exchangeDtoMapper::toDto)
          .map(dto -> FindOneResponse.newBuilder().setExchange(dto).build())
          .orElseGet(() -> FindOneResponse.newBuilder().build());

      sendReply(event, response);
    };
  }

  private FindOneRequest parseFindOne(CloudEvent event) {
    final byte[] bytes = extractBytes(event);
    try {
      return FindOneRequest.parseFrom(bytes);
    } catch (com.google.protobuf.InvalidProtocolBufferException ex) {
      throw new IllegalArgumentException("Failed to decode FindOneRequest payload", ex);
    }
  }

  private Optional<String> resolveExchangeId(FindOneRequest request) {
    if (!request.hasCriterion()) {
      return Optional.empty();
    }
    final Criterion criterion = request.getCriterion();
    if (!criterion.hasFilter()) {
      return Optional.empty();
    }

    final CriterionFilter filter = criterion.getFilter();
    switch (filter.getFilterCase()) {
      case BYENUM:
        return Optional.of(filter.getByEnum().name());
      case BYID:
        return extractStructString(filter.getById());
      case FILTER_NOT_SET:
      default:
        return Optional.empty();
    }
  }

  private Optional<String> extractStructString(Struct struct) {
    if (struct == null) {
      return Optional.empty();
    }
    if (struct.getFieldsMap().containsKey("value")) {
      final Value value = struct.getFieldsMap().get("value");
      if (value != null && value.hasStringValue()) {
        return Optional.of(value.getStringValue());
      }
    }
    return Optional.empty();
  }

  private void sendReply(CloudEvent event, com.google.protobuf.Message response) {
    final String replyTo = eventSender.resolveReplyTo(event);
    if (replyTo == null || replyTo.isBlank()) {
      return;
    }

    final boolean sent = eventSender.send(replyTo, response);
    if (!sent) {
      final String eventId = event.getId();
      log.error("Failed to dispatch FindOneResponse for event {}", eventId);
    }
  }

  private byte[] extractBytes(CloudEvent event) {
    final CloudEventData data = event.getData();
    if (data == null) {
      throw new IllegalArgumentException("CloudEvent has no data");
    }

    return data.toBytes();
  }
}
