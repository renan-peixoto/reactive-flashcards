package br.edu.reactiveflashcard.api.controller;

import br.edu.reactiveflashcard.api.controller.request.StudyRequest;
import br.edu.reactiveflashcard.api.controller.response.QuestionResponse;
import br.edu.reactiveflashcard.api.mapper.StudyMapper;
import br.edu.reactiveflashcard.domain.service.StudyService;
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
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest request){
        return studyService.start( studyMapper.toDocument( request ) )
                .doFirst( () -> log.info( "==== Trying to create a study with the following request {}", request ) )
                .map(document -> studyMapper.toResponse( document.getLastPendingQuestion() ) );
    }


}
