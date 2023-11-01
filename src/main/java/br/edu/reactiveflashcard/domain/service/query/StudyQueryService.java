package br.edu.reactiveflashcard.domain.service.query;

import br.edu.reactiveflashcard.domain.document.Question;
import br.edu.reactiveflashcard.domain.document.StudyDocument;
import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import br.edu.reactiveflashcard.domain.repository.StudyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.*;

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
                        Mono.error( new NotFoundException( STUDY_DECK_NOT_FOUND.params( userId, deckId ).getMessage() ) ) ) );
    }

    public Mono<StudyDocument> findById(final String id) {
        return studyRepository.findById( id )
                .filter( Objects::nonNull )
                .switchIfEmpty( Mono.defer( () ->
                        Mono.error( new NotFoundException( STUDY_NOT_FOUND.params( id ).getMessage() ) ) ) );
    }

    public Mono<Question> getLastPendingQuestion(final String id) {
        return findById( id )
                .doFirst( () -> log.info( "Getting study with id {}", id ) )
                .filter( study -> BooleanUtils.isFalse( study.complete() ) )
                .switchIfEmpty( Mono.defer( () ->
                        Mono.error( new NotFoundException( STUDY_QUESTION_NOT_FOUND.params( id ).getMessage() ) ) ) )
                .flatMapMany( study -> Flux.fromIterable( study.questions() ) )
                .filter( Question::isAnswered )
                .doFirst( () -> log.info( "Getting the current pending question in study {}", id ) )
                .single();
    }
}
