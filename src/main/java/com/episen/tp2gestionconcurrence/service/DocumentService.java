package com.episen.tp2gestionconcurrence.service;

import com.episen.tp2gestionconcurrence.model.Document;
import com.episen.tp2gestionconcurrence.model.DocumentSummary;
import com.episen.tp2gestionconcurrence.model.DocumentsList;
import com.episen.tp2gestionconcurrence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;


    public DocumentsList documentsGet(Pageable pageable) {
        Page<Document> pages = documentRepository.findAll(pageable);
        return fromPageToDocumentsList(pages);
    }


    public DocumentsList fromPageToDocumentsList(Page page) {
        DocumentsList results = new DocumentsList();
        results.setData((ArrayList<DocumentSummary>) page.getContent());
        results.setPage(page.getNumber());
        results.setNbElements((int) page.getTotalElements());
        return results;
    }

    public DocumentsList documentsPost(Document document) {
        UserDetails userDetails = getUserDetails();
        document.setStatus(Document.StatusEnum.CREATED);
        document.setCreated(LocalDateTime.now());
        document.setUpdated(LocalDateTime.now());
        document.setCreator(userDetails.getUsername());
        document.setEditor(userDetails.getUsername());
        Document createdDocument = documentRepository.insert(document);
        ArrayList<DocumentSummary> data = new ArrayList<>();
        data.add(createdDocument.getSummary());
        return DocumentsList.builder()
                .page(0)
                .nbElements(1)
                .data(data)
                .build();
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }
}
