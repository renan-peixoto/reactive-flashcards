package br.edu.reactiveflashcard.domain.service;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import br.edu.reactiveflashcard.domain.repository.UserRepository;
import br.edu.reactiveflashcard.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public Mono<UserDocument> save(final UserDocument document) {
        return userRepository.save( document )
                .doFirst( () -> log.info( "==== Try to save a follow user {}", document ) );
    }

    public Mono<UserDocument> update(final UserDocument document) {
        return userQueryService.findById( document.id() )
                .map( user -> document.toBuilder()
                        .createdAt( user.createdAt() )
                        .updatedAt( user.updatedAt() )
                        .build() )
                .flatMap( userRepository::save )
                .doFirst( () -> log.info( "==== Try to update a user with follow info {}", document ) );
    }

    public Mono<Void> delete(final String id) {
        return userQueryService.findById( id )
                .flatMap( userRepository::delete )
                .doFirst( () -> log.info("==== Try to update a user with follow id {}", id ));
    }



}
