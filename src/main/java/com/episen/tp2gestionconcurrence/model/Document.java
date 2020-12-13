package com.episen.tp2gestionconcurrence.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Document {
    private String documentId;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String title;
    private String creator;
    private String editor;
    private String body;
    private StatusEnum status;

    public enum StatusEnum{
        CREATED("CREATED"),
        VALIDATED("VALIDATED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(value);
        }

        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
