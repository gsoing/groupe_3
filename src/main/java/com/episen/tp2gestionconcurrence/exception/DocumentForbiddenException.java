package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DocumentForbiddenException extends AbstractDocumentException {
    public static final DocumentForbiddenException DEFAULT = new DocumentForbiddenException();
    private static final String CODE = "err.func.forbidden.access";
    private static final String MESSAGE = "Forbidden Access";

    public DocumentForbiddenException() {
        super(HttpStatus.BAD_REQUEST, ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL)
                .errors(List.of(new ErrorDefinitionError(CODE, MESSAGE))).build());
    }
}
