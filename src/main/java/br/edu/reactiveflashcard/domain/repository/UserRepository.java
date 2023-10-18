package br.edu.reactiveflashcard.domain.repository;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {

    Mono<UserDocument> findByEmail(final String email);

}
