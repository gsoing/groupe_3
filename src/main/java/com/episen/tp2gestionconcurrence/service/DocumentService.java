package com.episen.tp2gestionconcurrence.service;

import com.episen.tp2gestionconcurrence.exception.DocumentCannotBeModifiedException;
import com.episen.tp2gestionconcurrence.exception.DocumentNotFoundException;
import com.episen.tp2gestionconcurrence.model.Document;
import com.episen.tp2gestionconcurrence.model.DocumentSummary;
import com.episen.tp2gestionconcurrence.model.DocumentsList;
import com.episen.tp2gestionconcurrence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
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

    public DocumentsList createDocument(Document document) {
        UserDetails userDetails = getUserDetails();
        document.setStatus(Document.StatusEnum.CREATED);
        document.setCreated(LocalDateTime.now());
        document.setUpdated(LocalDateTime.now());
        document.setCreator(userDetails.getUsername());
        document.setEditor(userDetails.getUsername());
        Document createdDocument = documentRepository.insert(document);
        ArrayList<DocumentSummary> data = new ArrayList<>();
        data.add(createdDocument.toSummary());
        return DocumentsList.builder()
                .page(0)
                .nbElements(1)
                .data(data)
                .build();
    }

    public Document getDocumentById(String documentId) {
        return documentRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
    }

    public Document updateDocumentById(String documentId, Document document) {
        Document toUpdateDocument = documentRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
        if (toUpdateDocument.getStatus().equals(Document.StatusEnum.VALIDATED))
            throw new DocumentCannotBeModifiedException();
        toUpdateDocument.setTitle(document.getTitle());
        toUpdateDocument.setBody(document.getBody());
        toUpdateDocument.setUpdated(LocalDateTime.now());
        toUpdateDocument.setEditor(getUserDetails().getUsername());
        return documentRepository.save(toUpdateDocument);
    }

    public void updateDocumentStatusById(String documentId, Document.StatusEnum documentStatus) {
        Document toUpdateDocument = documentRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
        if (toUpdateDocument.getStatus().equals(Document.StatusEnum.VALIDATED))
            throw new DocumentCannotBeModifiedException();
        toUpdateDocument.setStatus(documentStatus);
        return;

    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }


}
