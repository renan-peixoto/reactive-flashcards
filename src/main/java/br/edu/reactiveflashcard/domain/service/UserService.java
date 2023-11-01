package br.edu.reactiveflashcard.domain.service;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import br.edu.reactiveflashcard.domain.exception.EmailAlreadyUsedException;
import br.edu.reactiveflashcard.domain.exception.NotFoundException;
import br.edu.reactiveflashcard.domain.repository.UserRepository;
import br.edu.reactiveflashcard.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static br.edu.reactiveflashcard.domain.exception.BaseErrorMessage.EMAIL_ALREADY_USED;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public Mono<UserDocument> save(final UserDocument document) {

        return userQueryService.findByEmail( document.email() )
                .doFirst( () -> log.info( "==== Try to save a follow user {}", document ) )
                .filter( Objects::isNull )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new EmailAlreadyUsedException( EMAIL_ALREADY_USED
                        .params( document.email() ).getMessage() ) ) ) )
                .onErrorResume( NotFoundException.class, e -> userRepository.save( document ) )
                ;

    }

    private Mono<Void> verifyEmail(final UserDocument document) {
        return userQueryService.findByEmail( document.email() )
                .filter( stored -> stored.id().equals( document.id() ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new EmailAlreadyUsedException( EMAIL_ALREADY_USED
                        .params( document.email() ).getMessage() ) ) ) )
                .onErrorResume( NotFoundException.class, e -> Mono.empty() )
                .then();
    }

    public Mono<UserDocument> update(final UserDocument document) {

        return verifyEmail( document )
                .then( userQueryService.findById( document.id() )
                        .map( user -> document.toBuilder()
                                .createdAt( user.createdAt() )
                                .updatedAt( user.updatedAt() )
                                .build() )
                        .flatMap( userRepository::save )
                        .doFirst( () -> log.info( "==== Try to update a user with follow info {}", document ) ) );

    }

    public Mono<Void> delete(final String id) {
        return userQueryService.findById( id )
                .flatMap( userRepository::delete )
                .doFirst( () -> log.info( "==== Try to update a user with follow id {}", id ) );
    }


}
