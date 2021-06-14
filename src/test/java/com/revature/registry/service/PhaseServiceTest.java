package com.revature.registry.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.registry.ProjectRegistryTrackingApplication;
import com.revature.registry.repository.PhaseRepository;

@SpringBootTest(classes = ProjectRegistryTrackingApplication.class)
@ExtendWith(SpringExtension.class)
public class PhaseServiceTest {
	
	@Autowired
    private PhaseService phaseService;
	
	@Mock
	private PhaseRepository phaseRepo;
	
	@BeforeEach
	public static void setup() {
		
	}

}
