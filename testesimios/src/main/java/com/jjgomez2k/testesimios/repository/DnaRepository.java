package com.jjgomez2k.testesimios.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jjgomez2k.testesimios.document.DnaDocument;
import java.math.BigDecimal;

public interface DnaRepository extends MongoRepository<DnaDocument, String> {
    BigDecimal countByType(String type);
}
