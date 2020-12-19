package com.episen.tp2gestionconcurrence.service;

import com.episen.tp2gestionconcurrence.model.Document;
import com.episen.tp2gestionconcurrence.model.DocumentSummary;
import com.episen.tp2gestionconcurrence.model.DocumentsList;
import com.episen.tp2gestionconcurrence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
}
