package com.episen.tp2gestionconcurrence.endpoint;

import com.episen.tp2gestionconcurrence.dto.UserDto;
import com.episen.tp2gestionconcurrence.model.*;
import com.episen.tp2gestionconcurrence.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(DocumentApiController.PATH)
@Slf4j
public class DocumentApiController {

    public static final String PATH = "/documents";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DocumentService documentService;

    @RequestMapping(
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<DocumentsList> documentsGet(@RequestParam(value = "page", required = false) Integer page,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               UriComponentsBuilder uriComponentsBuilder){
        Pageable pageable = PageRequest.of(page, pageSize);
        DocumentsList pageResult = documentService.documentsGet(pageable);

        return new ResponseEntity<DocumentsList>(HttpStatus.ACCEPTED);
    }
    @RequestMapping(
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<DocumentsList> documentsPost(@RequestBody Document document){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(documentService.createDocument(document));
    }

    @RequestMapping(value = "/{documentId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Object> documentsDocumentIdGet(@PathVariable("documentId") String documentId){
        Document document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    @RequestMapping(value = "/{documentId}",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Object> documentsDocumentIdPut(@PathVariable("documentId") String documentId, @RequestBody Document document){
        Document updatedDocument = documentService.updateDocumentById(documentId, document);
        return ResponseEntity.ok(updatedDocument);
    }

    @RequestMapping(value = "/{documentId}/status",
            consumes = { "text/plain" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Document> documentsDocumentIdStatusPut(@PathVariable("documentId") String documentId, @RequestBody Document.StatusEnum document){
        return new ResponseEntity<Document>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value="/{documentId}/lock",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Lock> documentsDocumentIdLockGet(@PathVariable("documentId") String documentId){
        return new ResponseEntity<Lock>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value="/{documentId}/lock",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<Lock> documentsDocumentIdLockPut(@PathVariable("documentId") String documentId, @RequestBody Lock lock){
        return new ResponseEntity<Lock>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(
            value="/{documentId}/lock",
            consumes = { "application/json" },
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    ResponseEntity<Lock> documentsDocumentIdLockDelete(@PathVariable("documentId") String documentId){
        return new ResponseEntity<Lock>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        return ResponseEntity
                .ok(
                        UserDto.builder()
                                .username(authentication.getName())
                                .roles(
                                        authentication.getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList())
                                )
                                .build());
    }

    @GetMapping
    @RequestMapping("/auth")
    public ResponseEntity<UserDto> getCurrentUser2(Authentication authentication) {
        return ResponseEntity
                .ok(
                        UserDto.builder()
                                .username(authentication.getName())
                                .roles(
                                        authentication.getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList())
                                )
                                .build());
    }
}
