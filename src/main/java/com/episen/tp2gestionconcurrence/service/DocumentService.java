package com.episen.tp2gestionconcurrence.service;

import com.episen.tp2gestionconcurrence.exception.DocumentCannotBeModifiedException;
import com.episen.tp2gestionconcurrence.exception.DocumentConflictException;
import com.episen.tp2gestionconcurrence.exception.DocumentForbiddenException;
import com.episen.tp2gestionconcurrence.exception.DocumentNotFoundException;
import com.episen.tp2gestionconcurrence.model.Document;
import com.episen.tp2gestionconcurrence.model.DocumentSummary;
import com.episen.tp2gestionconcurrence.model.DocumentsList;
import com.episen.tp2gestionconcurrence.repository.DocumentRepository;
import com.episen.tp2gestionconcurrence.repository.LockRepository;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;


@Slf4j
@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private LockRepository lockRepository;

    public DocumentsList documentsGet(Pageable pageable) {
        Page<Document> pages = documentRepository.findAll(pageable);
        return fromPageToDocumentsList(pages);
    }


    public DocumentsList fromPageToDocumentsList(Page page) {
        DocumentsList results = new DocumentsList();
        List<Document> docs = page.getContent();
        ArrayList<DocumentSummary> sums = new ArrayList<>();
        for (Document d: docs) {
            sums.add(d.toSummary());
        }
        results.setData(sums);
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

    public Document updateDocumentById(String documentId, Document document, String etag) {
        Document toUpdateDocument = documentRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
        //checking pessimistic lock owner
        lockRepository.findByDocumentId(documentId).ifPresent(lock -> {
            if (!lock.getOwner().equals(getUserDetails().getUsername()))
                throw new DocumentForbiddenException();
        });
        // checking optimistic lock with etag
        if(!toUpdateDocument.getEtag().equals(etag))
            throw DocumentConflictException.DEFAULT;

        //checking if Document is already validated
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
        documentRepository.save(toUpdateDocument);
        return;

    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }


}
