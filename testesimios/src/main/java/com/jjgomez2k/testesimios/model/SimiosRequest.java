package com.jjgomez2k.testesimios.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class SimiosRequest {

    private String[] dna;
    public boolean isEmptyDna(){
        return this.dna == null || this.dna.length == 0;
    }
    public String getDnaHash(){
        return Arrays.stream(this.dna).reduce("", String::concat); //Cleans the input array
    }

}
