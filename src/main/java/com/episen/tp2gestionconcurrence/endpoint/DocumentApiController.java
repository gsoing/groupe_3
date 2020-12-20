package com.episen.tp2gestionconcurrence.endpoint;

import com.episen.tp2gestionconcurrence.model.*;
import com.episen.tp2gestionconcurrence.service.DocumentService;
import com.episen.tp2gestionconcurrence.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Optional;

@RestController
@RequestMapping(DocumentApiController.PATH)
@Slf4j
public class DocumentApiController {

    public static final String PATH = "/documents";

    @Autowired
    private LockService lockService;

    @Autowired
    private DocumentService documentService;

    @RequestMapping(
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<DocumentsList> documentsGet( @PageableDefault(page = 0, size = 20) Pageable pageable,
                                               UriComponentsBuilder uriComponentsBuilder){
        log.info("GET /documents :  (" + pageable.getPageNumber() + "), pageSize(" + pageable.getPageSize() + ")");
        DocumentsList pageResult = documentService.documentsGet(pageable);

        return ResponseEntity.ok(pageResult);
    }
    @RequestMapping(
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<DocumentsList> documentsPost(@RequestBody Document document){
        log.info("POST /documents : document " + document.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(documentService.createDocument(document));
    }

    @RequestMapping(value = "/{documentId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Object> documentsDocumentIdGet(@PathVariable("documentId") String documentId){
        log.info("GET /documents/{documentId} :  document id : " + documentId);
        Document document = documentService.getDocumentById(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(document.getEtag())
                .body(document);
    }

    @RequestMapping(value = "/{documentId}",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Object> documentsDocumentIdPut(@PathVariable("documentId") String documentId,
                                                  @RequestBody Document document,
                                                  @RequestHeader(value = "etag", defaultValue = "0") String etag){
        log.info("PUT /documents/{documentId} : document id '" + documentId + "' and document '" + document.toString());
        log.info("PUT /documents/{documentId} : etag: " + etag);

        Document updatedDocument = documentService.updateDocumentById(documentId, document, etag);
        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(updatedDocument.getEtag())
                .body(updatedDocument);
    }

    @RequestMapping(value = "/{documentId}/status",
            consumes = { "text/plain" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody String documentStatus){
        log.info("PUT /documents/{documentId}/status : document id '" + documentId + "' and status '" + documentStatus + "'");
        documentService.updateDocumentStatusById(documentId, Document.StatusEnum.fromValue(documentStatus));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(
            value="/{documentId}/lock",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Lock> documentsDocumentIdLockGet(@PathVariable("documentId") String documentId){
        log.info("GET /documents/{documentId}/lock :  document id :" + documentId);
        Optional<Lock> lock = lockService.getLock(documentId);
        if(lock.isPresent()) {
            log.info("Get Lock Document:"+ lock.toString());
            return ResponseEntity.status(HttpStatus.OK).body(lock.get());
        }
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(
            value="/{documentId}/lock",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Lock> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId){
        log.info("PUT /documents/{documentId}/lock : document id '" + documentId );
        Lock lock = lockService.lock(documentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lock);
    }

    @RequestMapping(
            value="/{documentId}/lock",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Lock> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId){
        log.info("DELETE /documents/{documentId}/lock :  document id '" + documentId);
        lockService.unLock(documentId);
        return new ResponseEntity<Lock>(HttpStatus.ACCEPTED);
    }


}
