package br.edu.reactiveflashcard.api.controller;

import br.edu.reactiveflashcard.api.controller.request.DeckRequest;
import br.edu.reactiveflashcard.api.controller.request.DeckResponse;
import br.edu.reactiveflashcard.api.mapper.DeckMapper;
import br.edu.reactiveflashcard.domain.service.DeckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("decks")
@Validated
@Slf4j
@RequiredArgsConstructor
public class DeckController {

    public final DeckService deckService;
    public final DeckMapper deckMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody final DeckRequest request) {
        return deckService.save( deckMapper.toDocument( request ) )
                .doFirst( () -> log.info( "==== saving deck with follow data {}", request ) )
                .map(deckMapper::toResponse);
    }
}
