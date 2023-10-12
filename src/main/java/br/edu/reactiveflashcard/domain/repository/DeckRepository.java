package br.edu.reactiveflashcard.domain.repository;

import br.edu.reactiveflashcard.domain.document.DeckDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DeckRepository extends ReactiveMongoRepository<DeckDocument, String> {
}
