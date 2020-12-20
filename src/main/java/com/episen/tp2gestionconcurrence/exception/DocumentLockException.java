package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DocumentLockException extends AbstractDocumentException {
    public static final DocumentLockException DEFAULT = new DocumentLockException();

    private static final String CODE = "err.func.lock.found";
    private static final String MESSAGE = "Lock already created";

    public DocumentLockException() {
        super(HttpStatus.BAD_REQUEST, ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL)
                .errors(List.of(new ErrorDefinitionError(CODE, MESSAGE))).build());
    }
}
