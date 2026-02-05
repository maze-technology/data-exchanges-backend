package tech.maze.data.exchanges.backend.api.eventstream;

import com.google.protobuf.Empty;
import io.cloudevents.CloudEvent;
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

      log.info("Received FindOne exchange event with id {}", event.getId());
      sendReply(event, Empty.getDefaultInstance());
    };
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

}
