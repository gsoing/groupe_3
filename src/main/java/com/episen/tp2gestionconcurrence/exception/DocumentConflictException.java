package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DocumentConflictException extends AbstractDocumentException {
    public static final DocumentConflictException DEFAULT = new DocumentConflictException();
    private static final String CODE= "err.func.conflict";
    private static final String MESSAGE = "The request could not be completed due to a conflict with the current state of the target resource";

    public DocumentConflictException() {
        super(HttpStatus.NOT_FOUND, ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL)
                .errors(List.of(new ErrorDefinitionError(CODE, MESSAGE))).build());
    }
}
