package br.edu.reactiveflashcard.domain.repository;

import br.edu.reactiveflashcard.domain.document.StudyDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudyRepository extends ReactiveMongoRepository<StudyDocument, String> {
}
