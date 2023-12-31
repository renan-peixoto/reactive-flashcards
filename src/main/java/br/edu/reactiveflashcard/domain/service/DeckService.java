package br.edu.reactiveflashcard.domain.service;

import br.edu.reactiveflashcard.domain.document.DeckDocument;
import br.edu.reactiveflashcard.domain.repository.DeckRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public Mono<DeckDocument> save(final DeckDocument document) {
        return deckRepository.save( document )
                .doFirst( () -> log.info( "==== Try to save a follow deck {}", document ) );
    }


}
