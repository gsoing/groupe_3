package com.episen.tp2gestionconcurrence.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DocumentsList {
    private int page;
    private int nbElements;
    private ArrayList<DocumentSummary> data;
}
