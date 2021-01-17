package com.jjgomez2k.testesimios.service.implementation;

import com.jjgomez2k.testesimios.document.DnaDocument;
import com.jjgomez2k.testesimios.model.SimiosRequest;
import com.jjgomez2k.testesimios.model.StatsResponse;
import com.jjgomez2k.testesimios.repository.DnaRepository;
import com.jjgomez2k.testesimios.service.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jjgomez2k.testesimios.util.MatrixUtils.*;

@Service
public class DnaServiceImplementation implements DnaService {
    private static final String HUMAN_TYPE = "HUMAN";
    private static final String SIMIO_TYPE = "SIMIO";

    @Autowired
    private DnaRepository dnaRepository;

    @Override
    public boolean isSimian(SimiosRequest input){
        if (input.isEmptyDna()) {
            return false;
        }
        if (!isValidDnaSequence(input.getDna())) {
            return false;
        }
        if (input.getDna().length < 4) { //isHuman
            dnaRepository.insert(DnaDocument.builder()
                    .id(input.getDnaHash())
                    .type(HUMAN_TYPE)
                    .build());
            return false;
        }
        return findSimiosDNA(input);
    }

    @Override
    public StatsResponse findDNAStats() {
        BigDecimal humans = dnaRepository.countByType(HUMAN_TYPE);
        BigDecimal simios = dnaRepository.countByType(SIMIO_TYPE);
        BigDecimal ratio = calculateProportion(humans, simios);

        return StatsResponse.builder()
                .count_human_dna(humans)
                .count_mutant_dna(simios)
                .ratio(ratio)
                .build();
    }

    private boolean findSimiosDNA(SimiosRequest simiosRequest) {
        int size = simiosRequest.getDna().length;
        char[][] matrix = new char[size][size];
        int counter = 0;

        for (String s : simiosRequest.getDna()) {
            matrix[counter] = s.toUpperCase().toCharArray();
            counter++;
        }

        AtomicInteger totalSimiosSequences = new AtomicInteger(0);
        findDiagonalyTopDown(size, matrix, totalSimiosSequences);
        findDiagonalyMidTop(size, matrix, totalSimiosSequences);
        findDiagonalyReverseMidTop(size, matrix, totalSimiosSequences);
        findDiagonalyReverseTopDown(size, matrix, totalSimiosSequences);
        containVerticalOrHorizontal(matrix, totalSimiosSequences);

        if (totalSimiosSequences.intValue() > 0) {
            dnaRepository.insert(DnaDocument.builder()
                    .id(simiosRequest.getDnaHash())
                    .type(SIMIO_TYPE)
                    .build());
            return true;
        } else {
            dnaRepository.insert(DnaDocument.builder()  //if Simio DNA sequences = 0 save as Human
                    .id(simiosRequest.getDnaHash())
                    .type(HUMAN_TYPE)
                    .build());
            return false;
        }
    }

    private BigDecimal calculateProportion(BigDecimal humans, BigDecimal simios) {
        BigDecimal ratio;
        if (simios.intValue() == 0) {
            ratio = BigDecimal.ZERO;
        } else if (humans.intValue() == 0) {
            ratio = simios;
        } else {
            ratio = new BigDecimal(((simios.intValue() * 100.0f) / humans.intValue()))
                    .divide(BigDecimal.valueOf(100));
        }
        return ratio.setScale(3, RoundingMode.HALF_UP);
    }
}
