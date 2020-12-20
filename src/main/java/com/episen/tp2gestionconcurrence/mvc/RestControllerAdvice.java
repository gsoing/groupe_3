package com.episen.tp2gestionconcurrence.mvc;

import com.episen.tp2gestionconcurrence.exception.DocumentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(DocumentNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(DocumentNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getErrorDefinition());
    }
}
