package com.revature.registry.controller;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.registry.ProjectRegistryBackendApplication;
import com.revature.registry.model.Iteration;
import com.revature.registry.service.IterationService;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



//TODO: Change JSON VALUES THAT STILL REFER TO PROJECT TESTING

@SpringBootTest(classes = ProjectRegistryBackendApplication.class)
@ExtendWith(SpringExtension.class)
class IterationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private IterationController iterationController;

    @MockBean
    private IterationService iterationService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(iterationController).build();
    }

    @Test
    void getAllIterationsReturnsData() throws Exception {

        // mock the return of getAllIteratons from IterationService

        Iteration iteration1 = new Iteration();
        iteration1.setId(12);
        iteration1.setStartDate(LocalDate.now().minusDays(1));
        iteration1.setEndDate(LocalDate.now());
        iteration1.setBatchId("107235");


        Iteration iteration2 = new Iteration();
        iteration2.setId(12);
        iteration2.setStartDate(LocalDate.now().minusDays(1));
        iteration2.setEndDate(LocalDate.now());
        iteration2.setBatchId("107239");

        List<Iteration> iterations = Lists.newArrayList(iteration1, iteration2);

        when(iterationService.getAllIterations()).thenReturn(new ResponseEntity<List<Iteration>>(iterations, HttpStatus.OK));

        // mock request to controller
        mockMvc.perform(get("/api/iteration")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(List.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("testProject1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("testProject2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(13));

    }

    @Test
    void should_get_appropriate_iteration_when_12isInput() throws Exception {

        // mock the return of getProjectById from ProjectService
        Iteration iteration = new Iteration();
        iteration.setId(12);
        iteration.setStartDate(LocalDate.now().minusDays(1));
        iteration.setEndDate(LocalDate.now());
        iteration.setBatchId("107235");

        when(iterationService.getIterationById((anyInt()))).thenReturn(new ResponseEntity<Iteration>(iteration, HttpStatus.OK));

        // mock request to controller
        mockMvc.perform(get("/api/project/id/12")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testProject"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("a project simply just for testing"));
    }

    @Test
    void should_create_iteration_when_valid_input() throws Exception {
        // mock the return of createIteration from IterationService
        Iteration iteration = new Iteration();
        iteration.setId(12);
        iteration.setStartDate(LocalDate.now().minusDays(1));
        iteration.setEndDate(LocalDate.now());
        iteration.setBatchId("107235");


        when(iterationService.createIteration(any(Iteration.class)))
                .thenReturn(new ResponseEntity<Iteration>(iteration, HttpStatus.CREATED));

        // mock request to controller
        mockMvc.perform(post("/api/iteration").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(iteration))).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testProject"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("a iteration simply just for testing"));
    }

    @Test
    void should_update_iteration_when_valid_input() throws JsonProcessingException, Exception {
        // mock the return of updateIteration from IterationService
        Iteration iteration = new Iteration();
        iteration.setId(12);
        iteration.setStartDate(LocalDate.now().minusDays(1));
        iteration.setEndDate(LocalDate.now());
        iteration.setBatchId("107235");


        when(iterationService.updateIterationById(anyInt(), any(Iteration.class)))
                .thenReturn(new ResponseEntity<Iteration>(iteration, HttpStatus.OK));

        // mock request to controller
        mockMvc.perform(put("/api/project/id/12").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(iteration))).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testProject"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("this has been updated"));
    }

    @Test
    void should_return_no_content_when_delete_isCalled() throws Exception {
        // mock the return of updateProject from ProjectService
        when(iterationService.deleteIterationById(anyInt())).thenReturn(new ResponseEntity<Iteration>(HttpStatus.NO_CONTENT));
        // mock request to controller
        mockMvc.perform(delete("/api/iteration/id/12")).andExpect(status().isNoContent());
    }

}
