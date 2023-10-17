package br.edu.reactiveflashcard.api.mapper;

import br.edu.reactiveflashcard.api.controller.request.DeckRequest;
import br.edu.reactiveflashcard.api.controller.request.DeckResponse;
import br.edu.reactiveflashcard.domain.document.DeckDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request);


    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckDocument toDocument(final DeckRequest request, final String id);


    DeckResponse toResponse(final DeckDocument document);
}
