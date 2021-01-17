package com.jjgomez2k.testesimios.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jjgomez2k.testesimios.model.SimiosRequest;
import com.jjgomez2k.testesimios.model.StatsResponse;
import com.jjgomez2k.testesimios.service.DnaService;

@RestController
@RequestMapping("/api")
public class TesteSimiosDnaController {

    @Autowired
    private DnaService dnaService;

    @PostMapping("/simian")
    public ResponseEntity<Void> validateSimiosDna(@RequestBody SimiosRequest simiosRequest){

        if(!dnaService.isSimian(simiosRequest))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> findStats(){

        return ResponseEntity.ok(dnaService.findDNAStats());
    }
}

