package com.episen.tp2gestionconcurrence.repository;

import com.episen.tp2gestionconcurrence.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, String> {
    @Override
    Page<Document> findAll(Pageable pageable);
}
