package com.episen.tp2gestionconcurrence.service;

import com.episen.tp2gestionconcurrence.exception.DocumentForbiddenException;
import com.episen.tp2gestionconcurrence.exception.DocumentLockException;
import com.episen.tp2gestionconcurrence.exception.DocumentNotFoundException;
import com.episen.tp2gestionconcurrence.model.Document;
import com.episen.tp2gestionconcurrence.model.Lock;
import com.episen.tp2gestionconcurrence.repository.DocumentRepository;
import com.episen.tp2gestionconcurrence.repository.LockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Vu que vous importez les mêmes repoository vous auriez pu merger les 2 services Document et Lock
 */

@Slf4j
@Service
public class LockService {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    LockRepository lockRepository;

    public Optional<Lock> getLock(String documentId) {
        return lockRepository.findByDocumentId(documentId);
    }

    public Lock lock(String documentId) {
        documentRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
        if(lockRepository.findByDocumentId(documentId).isPresent()) {
            Lock lock = lockRepository.findByDocumentId(documentId).get();
            log.info("Lock Document:"+ lock.toString());
            throw DocumentLockException.DEFAULT;
        }else{
            OffsetDateTime offsetDateTime = OffsetDateTime.now();
            Lock lock = new Lock(documentId, getUserDetails().getUsername(), offsetDateTime);
            log.info("Lock Document:"+ lock.toString());
            return lockRepository.save(lock);
        }
    }

    public void unLock(String documentId) {
        Lock lock = lockRepository.findByDocumentId(documentId).orElseThrow(DocumentNotFoundException::new);
        if(lock.getOwner().equals(getUserDetails().getUsername())) {
            lockRepository.deleteBydocumentId(documentId);
            log.info("UnLock Document:"+ lock.toString());
        }
        else throw DocumentForbiddenException.DEFAULT;
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }
}
