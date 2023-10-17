package br.edu.reactiveflashcard.api.mapper;

import br.edu.reactiveflashcard.api.controller.request.UserRequest;
import br.edu.reactiveflashcard.api.controller.response.UserResponse;
import br.edu.reactiveflashcard.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);


    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request, final String id);


    UserResponse toResponse(final UserDocument document);

}
