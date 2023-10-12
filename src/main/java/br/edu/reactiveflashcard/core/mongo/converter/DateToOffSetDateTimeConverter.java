package br.edu.reactiveflashcard.core.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

public class DateToOffSetDateTimeConverter implements Converter<OffsetDateTime, Date> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Date convert(OffsetDateTime source) {
        return Date.from( source.toInstant() );
    }
}
