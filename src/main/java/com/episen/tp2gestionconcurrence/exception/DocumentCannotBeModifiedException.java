package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DocumentCannotBeModifiedException extends AbstractDocumentException {

    private static final String CANNOT_BE_MODIFIED_CODE = "err.func.cannotbemodified";
    private static final String CANNOT_BE_MODIFIED_MESSAGE = "Document cannot be modified";

    public DocumentCannotBeModifiedException() {
        super(HttpStatus.BAD_REQUEST, ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL)
                .errors(List.of(new ErrorDefinitionError(CANNOT_BE_MODIFIED_CODE, CANNOT_BE_MODIFIED_MESSAGE))).build());
    }
}
