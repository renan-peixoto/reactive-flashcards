package br.edu.reactiveflashcard.api.controller;

import br.edu.reactiveflashcard.api.controller.request.StudyRequest;
import br.edu.reactiveflashcard.api.controller.response.QuestionResponse;
import br.edu.reactiveflashcard.api.mapper.StudyMapper;
import br.edu.reactiveflashcard.core.validation.MongoId;
import br.edu.reactiveflashcard.domain.service.StudyService;
import br.edu.reactiveflashcard.domain.service.query.StudyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("studies")
@Validated
@Slf4j
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;
    private final StudyMapper studyMapper;
    private final StudyQueryService studyQueryService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest request) {
        return studyService.start( studyMapper.toDocument( request ) )
                .doFirst( () -> log.info( "==== Trying to create a study with the following request {}", request ) )
                .map( document -> studyMapper.toResponse( document.getLastPendingQuestion(), document.id() ) );
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<QuestionResponse> getCurrentQuestion(@Valid @PathVariable @MongoId(message = "{studyController.id}") final String id) {
        return studyQueryService.getLastPendingQuestion( id )
                .doFirst( () -> log.info( "=== Trying to get a next question in study {}", id ) )
                .map( question -> studyMapper.toResponse( question, id ) );
    }


}
