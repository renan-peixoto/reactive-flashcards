package br.edu.reactiveflashcard.api.controller;

import br.edu.reactiveflashcard.api.controller.request.UserRequest;
import br.edu.reactiveflashcard.api.controller.response.UserResponse;
import br.edu.reactiveflashcard.api.mapper.UserMapper;
import br.edu.reactiveflashcard.core.validation.MongoId;
import br.edu.reactiveflashcard.domain.service.UserService;
import br.edu.reactiveflashcard.domain.service.query.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("users")
@Validated
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserQueryService userQueryService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody final UserRequest request) {
        return userService.save( userMapper.toDocument( request ) )
                .doFirst( () -> log.info( "==== saving user with follow data {}", request ) )
                .map( userMapper::toResponse );
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> findById(@PathVariable @Valid @MongoId(message = "{userController.id}") final String id) {
        return userQueryService.findById( id )
                .doFirst( () -> log.info( "==== finding a user with follow id {}", id ) )
                .map( userMapper::toResponse );
    }


}
