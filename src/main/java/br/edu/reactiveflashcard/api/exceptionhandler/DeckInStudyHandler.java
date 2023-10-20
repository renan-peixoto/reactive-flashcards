package br.edu.reactiveflashcard.api.exceptionhandler;

import br.edu.reactiveflashcard.domain.exception.DeckInStudyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@Component
public class DeckInStudyHandler extends AbstractHandleException<DeckInStudyException> {
    public DeckInStudyHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, DeckInStudyException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, CONFLICT );
                    return ex.getMessage();
                } )
                .map( message -> buildError( CONFLICT, message ) )
                .doFirst( () -> log.error( "==== DeckInStudyException   : ", ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
