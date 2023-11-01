package br.edu.reactiveflashcard.domain.service.query;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import br.edu.reactiveflashcard.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
@Slf4j
@AllArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public Mono<UserDocument> findById(final String id) {
        return userRepository.findById( id )
                .doFirst( () -> log.info( "==== try to find user with id {}", id ) )
                .filter( Objects::nonNull )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( USER_NOT_FOUND.params( "id", id ).getMessage() ) ) ) );

    }

    public Mono<UserDocument> findByEmail(final String email) {
        return userRepository.findByEmail( email )
                .doFirst( () -> log.info( "==== try to find user with email {}", email ) )
                .filter( Objects::nonNull )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( USER_NOT_FOUND.params( "email", email ).getMessage() ) ) ) );

    }


}
