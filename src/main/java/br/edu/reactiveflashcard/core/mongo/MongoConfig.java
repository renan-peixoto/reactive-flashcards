package br.edu.reactiveflashcard.core.mongo;

import br.edu.reactiveflashcard.core.mongo.converter.DateToOffSetDateTimeConverter;
import br.edu.reactiveflashcard.core.mongo.converter.OffSetDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MongoConfig {

    @Bean
    MongoCustomConversions mongoCustomConversions() {
        final List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add( new OffSetDateTimeToDateConverter() );
        converters.add( new DateToOffSetDateTimeConverter() );
        return new MongoCustomConversions( converters );
    }


}
