package com.jjgomez2k.testesimios.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Dna")
@Data
@Builder
public class DnaDocument {
    @Id
    private String id;
    private String dna;
    private String type;    //HUMAN or SIMIO
}