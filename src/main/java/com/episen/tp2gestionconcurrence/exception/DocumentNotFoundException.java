package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DocumentNotFoundException extends AbstractDocumentException {

    private static final String NOT_FOUND_CODE= "err.func.documentnotfound";
    private static final String NOT_FOUND_MESSAGE = "Unknown document with given id";

    public DocumentNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL)
                .errors(List.of(new ErrorDefinitionError(NOT_FOUND_CODE, NOT_FOUND_MESSAGE))).build());
    }
}
