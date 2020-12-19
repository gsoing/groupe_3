package com.episen.tp2gestionconcurrence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Data
public class Document implements Serializable {
    @Id
    private String documentId;

    private LocalDateTime created;
    private LocalDateTime updated;
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

    public DocumentSummary getSummary(){
        return DocumentSummary.builder()
                .documentId(documentId)
                .created(created)
                .updated(updated)
                .title(title)
                .build();
    }
}
