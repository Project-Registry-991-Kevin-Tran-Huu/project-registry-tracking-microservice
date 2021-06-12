package com.revature.registry.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.revature.registry.model.Iteration;
import com.revature.registry.repository.IterationRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;





@Service
@Slf4j
public class IterationService {
    private static Logger log = Logger.getLogger(IterationService.class);
    
@Autowired
private IterationRepository iterationRepository;

public ResponseEntity<List<Iteration>> getAllIterations() {

return ResponseEntity.ok(iterationRepository.findAll());
}

public ResponseEntity<Iteration> getIterationById(int id) {
    Optional<Iteration> iteration = iterationRepository.findById(id);
    if (iteration.isPresent()) {

        return ResponseEntity.ok(iteration.get());
    }
    log.error("Unable to GET. Iteration with id " + id);
    return ResponseEntity.badRequest().build();
}

public ResponseEntity<Iteration> createIteration(Iteration iteration) {
    Iteration savedIteration = iterationRepository.save(iteration);

    String location = String.format("/api/iteration/%s", savedIteration.getId());
    return ResponseEntity.created(URI.create(location)).body(savedIteration);
}

public ResponseEntity<Iteration> updateIterationById(int id, Iteration newIteration) {
    Optional<Iteration> iteration = iterationRepository.findById(id);
    if (iteration.isPresent()) {
        newIteration.setId(id);
        iterationRepository.save(newIteration);
        log.debug("Iteration with id: " + id + " Updated with the following properties: " + newIteration );
        return ResponseEntity.ok(iteration.get());
    }

    log.error("Unable to update. Iteration with id " + id + " not found.");
    return ResponseEntity.badRequest().build();
}

public ResponseEntity<Iteration> deleteIterationById(int id) {
    Optional<Iteration> iteration = iterationRepository.findById(id);
    if (iteration.isPresent()) {
        iterationRepository.deleteById(id);
        log.debug("Iteration Deleted with id: " + id);
        return ResponseEntity.noContent().build();
    }
    log.error("Unable to DELETE. Iteration with id:" + id + " not found.");
    return ResponseEntity.badRequest().build();
}


}
