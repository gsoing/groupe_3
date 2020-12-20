package com.episen.tp2gestionconcurrence.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDefinitionError {
    private String errorCode;
    private String errorMessage;
}
