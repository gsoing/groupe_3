package com.episen.tp2gestionconcurrence.exception;

import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractDocumentException extends RuntimeException{

    private final transient ErrorDefinition errorDefinition;
    private final HttpStatus httpStatus;

    public AbstractDocumentException(HttpStatus httpStatus, ErrorDefinition errorDefinition) {
        this.errorDefinition = errorDefinition;
        this.httpStatus = httpStatus;
    }
}
