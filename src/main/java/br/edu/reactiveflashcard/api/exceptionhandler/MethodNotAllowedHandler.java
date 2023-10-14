package br.edu.reactiveflashcard.api.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.GENERIC_METHOD_NOT_ALLOW;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
public class MethodNotAllowedHandler extends AbstractHandleException<MethodNotAllowedException> {

    public MethodNotAllowedHandler(ObjectMapper objectMapper) {
        super( objectMapper );
    }

    @Override
    public Mono<Void> handlerException(ServerWebExchange exchange, MethodNotAllowedException ex) {
        return Mono.fromCallable( () -> {
                    prepareExchange( exchange, METHOD_NOT_ALLOWED );
                    return GENERIC_METHOD_NOT_ALLOW.params( exchange.getRequest().getMethod().name() ).getMessage();
                } )
                .map( message -> buildError( METHOD_NOT_ALLOWED, message ) )
                .doFirst( () -> log.error( "==== MethodNotAllowedException: Method [{}] is not allowed at [{}]",
                        exchange.getRequest().getMethod(), exchange.getRequest().getPath().value(), ex ) )
                .flatMap( response -> writeResponse( exchange, response ) );
    }
}
