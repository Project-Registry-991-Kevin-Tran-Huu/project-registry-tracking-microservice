package com.revature.registry.controller;

import java.util.List;

import com.revature.registry.model.Phase;
import com.revature.registry.service.PhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping(value = "/api/phase", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class PhaseController {
    @Autowired
    private PhaseService phaseService;

    @GetMapping("")
    public ResponseEntity<List<Phase>> getAllPhases() {
        return phaseService.getAllPhases();
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Phase> getPhaseById(@PathVariable("id") int id) {
        return phaseService.getPhaseById(id);
    }
}
