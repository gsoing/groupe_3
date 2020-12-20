package com.episen.tp2gestionconcurrence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lock {
    String documentId;
    String owner;
    OffsetDateTime created;
}
