package br.edu.reactiveflashcard.domain.service;

import br.edu.reactiveflashcard.domain.document.Card;
import br.edu.reactiveflashcard.domain.document.StudyDocument;
import br.edu.reactiveflashcard.domain.mapper.StudyDomainMapper;
import br.edu.reactiveflashcard.domain.repository.StudyRepository;
import br.edu.reactiveflashcard.domain.service.query.DeckQueryService;
import br.edu.reactiveflashcard.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class StudyService {

    private final UserQueryService userQueryService;
    private final DeckQueryService deckQueryService;
    private final StudyDomainMapper studyDomainMapper;
    private final StudyRepository studyRepository;

    public Mono<StudyDocument> start(final StudyDocument document) {
        return userQueryService.findById( document.userId() )
                .flatMap( user -> deckQueryService.findById( document.studyDeck().deckId() ) )
                .flatMap( deck -> getCards( document, deck.cards() ) )
                .map( study -> study.toBuilder()
                        .question( studyDomainMapper.generateRandomQuestion( study.studyDeck().cards() ) )
                        .build() )
                .doFirst( () -> log.info( "==== generating a first random question" ) )
                .flatMap( studyRepository::save )
                .doOnSuccess( study -> log.info( "==== The following stidy was save {}", study ) );
    }

    public Mono<StudyDocument> getCards(final StudyDocument document, final Set<Card> cards) {
        return Flux.fromIterable( cards )
                .doFirst( () -> log.info( "==== copy cards to new study" ) )
                .map( studyDomainMapper::toStudyCard )
                .collectList()
                .map( studyCards -> document.studyDeck().toBuilder().cards( Set.copyOf( studyCards ) ).build() )
                .map( studyDeck -> document.toBuilder().studyDeck( studyDeck ).build() );
    }
}
