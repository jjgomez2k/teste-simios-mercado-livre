package com.jjgomez2k.testesimios.service.implementation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.jjgomez2k.testesimios.model.SimiosRequest;
import com.jjgomez2k.testesimios.model.StatsResponse;
import com.jjgomez2k.testesimios.repository.DnaRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class DnaServiceImplementationTest {

    @InjectMocks
    private DnaServiceImplementation dnaService;

    @MockBean
    private DnaRepository dnaRepository;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldVerifyIsSimianSuccessfully(){
        assertFalse(dnaService.isSimian(getMockDnaEmptyRequest()));
        assertFalse(dnaService.isSimian(getMockDnaInvalidArrayRequest()));
        assertFalse(dnaService.isSimian(getMockDnaInvalidLetterRequest()));
        assertTrue(dnaService.isSimian(getMockDnaSimiosRequest()));
        assertFalse(dnaService.isSimian(getMockDnaHumanRequest()));
        assertFalse(dnaService.isSimian(getMockDnaHuman2Request()));
    }

    @Test
    public void shouldFindStatsSuccessfully(){
        whenCountbyTypeReturnValue("HUMAN", 10);
        whenCountbyTypeReturnValue("SIMIO", 4);

        StatsResponse stats = dnaService.findDNAStats();
        assertNotNull(stats);
        assertNotNull(stats.getRatio());

        whenCountbyTypeReturnValue("HUMAN", 0);
        whenCountbyTypeReturnValue("SIMIO", 4);

        stats = dnaService.findDNAStats();
        assertNotNull(stats);
        assertNotNull(stats.getRatio());

        whenCountbyTypeReturnValue("HUMAN", 5);
        whenCountbyTypeReturnValue("SIMIO", 0);

        stats = dnaService.findDNAStats();
        assertNotNull(stats);
        assertNotNull(stats.getRatio());

    }

    private void whenCountbyTypeReturnValue(String type, int value) {
        Mockito.when(dnaRepository
                .countByType(Mockito.eq(type)))
                .thenReturn(BigDecimal.valueOf(value));
    }

    private SimiosRequest getMockDnaInvalidArrayRequest(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {"CACA","CCGC","CAGG","GGAG","CCAG"};
        input.setDna(dna);
        return  input;
    }


    private SimiosRequest getMockDnaInvalidLetterRequest(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {"CACA","QCGC","CAGX","GGAG"};
        input.setDna(dna);
        return  input;
    }

    private SimiosRequest getMockDnaEmptyRequest(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {};
        input.setDna(dna);
        return  input;
    }

    private SimiosRequest getMockDnaSimiosRequest(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {"acgg","aagg","acag","acga"};
        input.setDna(dna);
        return  input;
    }

    private SimiosRequest getMockDnaHumanRequest(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {"acg","aag","acg"};
        input.setDna(dna);
        return  input;
    }

    private SimiosRequest getMockDnaHuman2Request(){
        SimiosRequest input = new SimiosRequest();
        String[] dna = {"AGGCGGCGTT", "TTGCGCCGTT",
                "ATATTGAGGG", "ATAACGATTC", "GGTGCATGTG",
                "TCTTCAAGGT", "CGGCGTATAA", "CGGCGTCCTC",
                "GCAGCGTTAC", "GCGGCGTTCA"};
        input.setDna(dna);
        return  input;
    }

}