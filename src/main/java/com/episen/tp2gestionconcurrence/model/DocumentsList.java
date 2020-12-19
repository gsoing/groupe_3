package com.episen.tp2gestionconcurrence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DocumentsList {
    private int page;
    private int nbElements;
    private ArrayList<DocumentSummary> data;
}
