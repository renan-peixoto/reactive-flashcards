package br.edu.reactiveflashcard.domain.service.query;

import br.edu.reactiveflashcard.domain.document.DeckDocument;
import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import br.edu.reactiveflashcard.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class DeckQueryService {

    private final DeckRepository deckRepository;

    public Mono<DeckDocument> findById(final String id) {
        return deckRepository.findById( id )
                .doFirst( () -> log.info("==== try to find deck with id {}", id ))
                .filter( Objects::nonNull )
                .switchIfEmpty( Mono.defer(() -> Mono.error( new NotFoundException( USER_NOT_FOUND.params( id ).getMessage() ) ) ) );
    }

    public Flux<DeckDocument> findAll() {
        return deckRepository.findAll()
                .doFirst( () -> log.info("==== try to get all decks" ) );
    }
}
