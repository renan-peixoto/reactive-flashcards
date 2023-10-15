package br.edu.reactiveflashcard.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Component
public class ResponseStatusHandler extends AbstractHandleException<ResponseStatusException> {
    public ResponseStatusHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, ResponseStatusException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, BAD_REQUEST );
                    return GENERIC_BAD_REQUEST.getMessage();
                } )
                .map( message -> buildError( BAD_REQUEST, message ) )
                .doFirst( () -> log.error( "==== ResponseStatusException: ", ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
