package br.edu.reactiveflashcard.domain.service;

import br.edu.reactiveflashcard.domain.document.UserDocument;
import br.edu.reactiveflashcard.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserDocument> save(final UserDocument document) {
        return userRepository.save( document )
                .doFirst( () -> log.info( "==== try to save a follow document {}", document ) );
    }

}
