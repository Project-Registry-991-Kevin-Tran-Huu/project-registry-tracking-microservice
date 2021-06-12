package com.revature.registry.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.revature.registry.model.Phase;
import com.revature.registry.repository.PhaseRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;




@Service
@Slf4j
public class PhaseService {
    private static Logger log = Logger.getLogger(PhaseService.class);

@Autowired
private PhaseRepository phaseRepository;

public ResponseEntity<List<Phase>> getAllPhases() {

return ResponseEntity.ok(phaseRepository.findAll());
}

public ResponseEntity<Phase> getPhaseById(int id) {
    Optional<Phase> phase = phaseRepository.findById(id);
    if (phase.isPresent()) {

        return ResponseEntity.ok(phase.get());
    }
    log.error("Unable to GET. Iteration with id " + id + " not found.");
    return ResponseEntity.badRequest().build();
}
}
