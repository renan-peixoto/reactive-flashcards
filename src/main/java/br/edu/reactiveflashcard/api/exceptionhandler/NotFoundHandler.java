package br.edu.reactiveflashcard.api.exceptionhandler;

import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class NotFoundHandler extends AbstractHandleException<NotFoundException> {
    public NotFoundHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, NotFoundException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, NOT_FOUND );
                    return ex.getMessage();
                } )
                .map( message -> buildError( NOT_FOUND, message ) )
                .doFirst( () -> log.error( "==== NotFoundException: ", ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
