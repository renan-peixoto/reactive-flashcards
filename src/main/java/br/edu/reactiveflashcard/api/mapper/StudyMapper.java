package br.edu.reactiveflashcard.api.mapper;

import br.edu.reactiveflashcard.api.controller.request.StudyRequest;
import br.edu.reactiveflashcard.api.controller.response.QuestionResponse;
import br.edu.reactiveflashcard.domain.document.Question;
import br.edu.reactiveflashcard.domain.document.StudyDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudyMapper {

    @Mapping( target = "id", ignore = true )
    @Mapping( target = "studyDeck.deckId", source = "deckId")
    @Mapping( target = "studyDeck.cards", ignore = true )
    @Mapping( target = "questions", ignore = true )
    @Mapping( target = "question", ignore = true )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping( target = "updatedAt", ignore = true )
    StudyDocument toDocument(final StudyRequest request);

    QuestionResponse toResponse(final Question question);
}
