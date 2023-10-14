package br.edu.reactiveflashcard.api.exceptionhandler;

import br.edu.reactiveflashcard.domain.exception.ReactiveFlashcardsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
public class ReactiveFlashcardsHandler extends AbstractHandleException<ReactiveFlashcardsException> {
    public ReactiveFlashcardsHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ReactiveFlashcardsException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, INTERNAL_SERVER_ERROR );
                    return GENERIC_EXCEPTION.getMessage();
                } )
                .map( message -> buildError( INTERNAL_SERVER_ERROR, message ) )
                .doFirst( () -> log.error( "==== ReactiveFlashcardsException: ", ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
