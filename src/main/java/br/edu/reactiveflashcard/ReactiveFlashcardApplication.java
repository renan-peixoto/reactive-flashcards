package br.edu.reactiveflashcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class ReactiveFlashcardApplication {

    public static void main(String[] args) {
        SpringApplication.run( ReactiveFlashcardApplication.class, args );
    }

}
