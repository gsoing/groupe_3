package com.episen.tp2gestionconcurrence.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDefinition {
    private ErrorTypeEnum errorType;
    private List<ErrorDefinitionError> errors;

    public enum ErrorTypeEnum {
        TECHNICAL("TECHNICAL"),
        FUNCTIONAL("FUNCTIONAL");

        private String value;

        ErrorTypeEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(value);
        }

        public static ErrorTypeEnum fromValue(String text) {
            for (ErrorTypeEnum b : ErrorTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
