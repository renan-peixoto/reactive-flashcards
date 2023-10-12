package br.edu.reactiveflashcard.core.mongo.provider;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Component("dateTimeProvider")
public class OffSetDateTimeProvider implements DateTimeProvider {
    /**
     * Returns the current time to be used as modification or creation date.
     *
     * @return
     */
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of( OffsetDateTime.now( UTC ) );
    }
}
