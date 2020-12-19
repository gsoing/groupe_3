package com.episen.tp2gestionconcurrence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class DocumentSummary {
    private String documentId;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String title;
}
