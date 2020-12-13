package com.episen.tp2gestionconcurrence.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DocumentSummary {
    private String documentId;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String title;
}
