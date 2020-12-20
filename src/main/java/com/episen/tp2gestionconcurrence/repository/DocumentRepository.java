package com.episen.tp2gestionconcurrence.repository;

import com.episen.tp2gestionconcurrence.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocumentRepository extends MongoRepository<Document, String> {
    @Override
    Page<Document> findAll(Pageable pageable);
    Optional<Document> findByDocumentId(String documentId);
}
