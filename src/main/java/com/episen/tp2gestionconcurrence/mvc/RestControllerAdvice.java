package com.episen.tp2gestionconcurrence.mvc;

import com.episen.tp2gestionconcurrence.exception.*;
import com.episen.tp2gestionconcurrence.model.ErrorDefinition;
import com.episen.tp2gestionconcurrence.model.ErrorDefinitionError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestControllerAdvice{

/*    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDefinition.builder()
                .errorType(ErrorDefinition.ErrorTypeEnum.TECHNICAL)
                .errors(List.of(new ErrorDefinitionError("err.tech.mediatypenotacceptable", ex.getMessage()))).build());
    }*/

    @ExceptionHandler({DocumentNotFoundException.class, DocumentCannotBeModifiedException.class,
            DocumentForbiddenException.class, DocumentLockException.class, DocumentConflictException.class})
    public final ResponseEntity<Object> handleNotFoundException(AbstractDocumentException ex, WebRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorDefinition());
    }
}
