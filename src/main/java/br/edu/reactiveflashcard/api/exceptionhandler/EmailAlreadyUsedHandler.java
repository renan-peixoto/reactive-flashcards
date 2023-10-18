package br.edu.reactiveflashcard.api.exceptionhandler;

import br.edu.reactiveflashcard.domain.exception.EmailAlreadyUsedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class EmailAlreadyUsedHandler extends AbstractHandleException<EmailAlreadyUsedException> {
    public EmailAlreadyUsedHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, EmailAlreadyUsedException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, BAD_REQUEST );
                    return ex.getMessage();
                } )
                .map( message -> buildError( BAD_REQUEST, message ) )
                .doFirst( () -> log.error( "==== EmailAlreadyUsedException: ", ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
