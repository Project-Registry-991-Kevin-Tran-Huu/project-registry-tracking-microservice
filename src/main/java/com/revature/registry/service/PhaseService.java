package com.revature.registry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.registry.model.Phase;
import com.revature.registry.repository.PhaseRepository;

@Service
public class PhaseService {
	
	@Autowired
	private PhaseRepository phaseRepository;

	public List<Phase> getAllPhases() {

		List<Phase> phase = phaseRepository.findAll();
		return phase;
	}

	public Optional<Phase>  getPhaseById(int id) {
		Optional<Phase> phase = phaseRepository.findById(id);
		if (phase.isPresent()) {

			return phase;
		}
		return null;
	}
}
