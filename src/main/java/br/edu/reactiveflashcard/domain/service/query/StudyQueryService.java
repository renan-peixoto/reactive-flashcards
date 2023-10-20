package br.edu.reactiveflashcard.domain.service.query;

import br.edu.reactiveflashcard.domain.document.StudyDocument;
import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import br.edu.reactiveflashcard.domain.repository.StudyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.STUDY_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class StudyQueryService {

    private StudyRepository studyRepository;

    public Mono<StudyDocument> findPendingStudyByUserIdAndDeckId(final String userId, final String deckId) {
        return studyRepository.findByUserIdAndCompleteFalseAndStudyDeck_DeckId( userId, deckId )
                .doFirst( () -> log.info( "==== Trying to get pending study with userId {} and deckId {}", userId, deckId ) )
                .filter( Objects::nonNull )
                .switchIfEmpty( Mono.defer( () ->
                        Mono.error( new NotFoundException( STUDY_NOT_FOUND.params( userId, deckId ).getMessage() ) ) ) );
    }
}
