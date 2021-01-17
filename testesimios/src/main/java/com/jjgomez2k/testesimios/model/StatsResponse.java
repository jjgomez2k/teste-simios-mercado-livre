package com.jjgomez2k.testesimios.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class StatsResponse {
    private BigDecimal count_mutant_dna; //Amount of non-human DNA sequences
    private BigDecimal count_human_dna; //Amount of human DNA sequences
    private BigDecimal ratio;   //Ratio of mutant and human DNA sequences
}
