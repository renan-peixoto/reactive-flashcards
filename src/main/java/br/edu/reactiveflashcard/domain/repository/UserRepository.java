package br.edu.reactiveflashcard.domain.repository;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
}
