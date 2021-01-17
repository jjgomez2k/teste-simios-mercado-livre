package com.jjgomez2k.testesimios.service;

import com.jjgomez2k.testesimios.model.SimiosRequest;
import com.jjgomez2k.testesimios.model.StatsResponse;

public interface DnaService {
    boolean isSimian(SimiosRequest input);
    StatsResponse findDNAStats();
}
