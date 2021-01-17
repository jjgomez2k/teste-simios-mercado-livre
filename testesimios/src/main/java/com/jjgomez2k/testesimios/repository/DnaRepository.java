package com.jjgomez2k.testesimios.repository;

import com.jjgomez2k.testesimios.document.DnaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;

public interface DnaRepository extends MongoRepository<DnaDocument, String> {
    BigDecimal countByType(String type);
}
