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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.revature.registry.ProjectRegistryTrackingApplication;
import com.revature.registry.model.Iteration;
import com.revature.registry.service.IterationService;



//TODO: Change JSON VALUES THAT STILL REFER TO PROJECT TESTING

@SpringBootTest(classes = ProjectRegistryTrackingApplication.class)
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

//    @Test
    void getAllIterationsReturnsData() throws Exception {

        // mock the return of getAllIteratons from IterationService

        Iteration iteration1 = new Iteration();
        iteration1.setId(12);
        LocalDate localDateNow = LocalDate.now();
        LocalDate startDate = localDateNow.minusDays(1);
        String startDateStr = startDate.toString();
        LocalDate endDate = localDateNow;
        String endDateStr = localDateNow.toString();
        iteration1.setStartDate(startDate);
        iteration1.setEndDate(endDate);
        iteration1.setBatchId("107235");


        Iteration iteration2 = new Iteration();
        iteration2.setId(13);
        iteration2.setStartDate(startDate);
        iteration2.setEndDate(endDate);
        iteration2.setBatchId("107239");

        List<Iteration> iterations = Lists.newArrayList(iteration1, iteration2);

        when(iterationService.getAllIterations()).thenReturn(new ResponseEntity<List<Iteration>>(iterations, HttpStatus.OK));

        // mock request to controller
        mockMvc.perform(get("/api/iteration")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(List.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startDate").value(startDateStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].endDate").value(endDateStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(13));

    }

//    @Test
    void should_get_appropriate_iteration_when_12isInput() throws Exception {

        // mock the return of getProjectById from ProjectService
        Iteration iteration = new Iteration();
        iteration.setId(12);
        LocalDate localDateNow = LocalDate.now();
        iteration.setStartDate(localDateNow.minusDays(1));
        iteration.setEndDate(localDateNow);
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
        LocalDate localDateNow = LocalDate.now();
        LocalDate startDate = localDateNow.minusDays(1);
        String startDateStr = startDate.toString();
        LocalDate endDate = localDateNow;
        String endDateStr = localDateNow.toString();
        iteration.setStartDate(startDate);
        iteration.setEndDate(endDate);
        iteration.setBatchId("107235");


        when(iterationService.createIteration(any(Iteration.class)))
                .thenReturn(new ResponseEntity<Iteration>(iteration, HttpStatus.OK));
        // mock request to controller
        mockMvc.perform(post("/api/iteration").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(iteration))).andExpect(status().isOk())
        .content(makeMapper().writeValueAsString(iteration))).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(startDateStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(endDateStr))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchId").value("107235"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(12));
    }

    private static final ObjectMapper makeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
//    @Test
    void should_update_iteration_when_valid_input() throws JsonProcessingException, Exception {
        // mock the return of updateIteration from IterationService
        Iteration iteration = new Iteration();
        iteration.setId(12);
        LocalDate localDateNow = LocalDate.now();
        iteration.setStartDate(localDateNow.minusDays(1));
        iteration.setEndDate(localDateNow);
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

//    @Test
    void should_return_no_content_when_delete_isCalled() throws Exception {
        // mock the return of updateProject from ProjectService
        when(iterationService.deleteIterationById(anyInt())).thenReturn(new ResponseEntity<Iteration>(HttpStatus.NO_CONTENT));
        // mock request to controller
        mockMvc.perform(delete("/api/iteration/id/12")).andExpect(status().isNoContent());
    }

}
