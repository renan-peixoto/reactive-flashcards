package br.edu.reactiveflashcard.domain.repository;

import br.edu.reactiveflashcard.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {

    Mono<StudyDocument> findByUserIdAndCompleteFalseAndStudyDeck_DeckId(final String userId, final String deckId);


}
