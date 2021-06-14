package com.revature.registry.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.registry.ProjectRegistryTrackingApplication;
import com.revature.registry.model.Status;
import com.revature.registry.repository.StatusRepository;
import com.revature.registry.service.StatusService;








@SpringBootTest(classes = ProjectRegistryTrackingApplication.class)
@ExtendWith(SpringExtension.class)
class StatusServiceTest {

    @Autowired
    private StatusService statusService;

    @MockBean
    private StatusRepository statusRepository;

    @Test
    void when_getStatusByID_return_correct_list() {
        // mock the return of getAllStatuss from ProjectRepository

        Status status1 = new Status();
        status1.setId(12);
        status1.setName("IN_ITERATION");

        Status status2 = new Status();
        status2.setId(12);
        status2.setName("CODE_FREEZE");

        List<Status> statusList = Lists.newArrayList(status1, status2);
        ResponseEntity<List<Status>> expected = new ResponseEntity<>(statusList, HttpStatus.OK);

        when(statusRepository.findAll()).thenReturn(statusList);

        // check to see if the method returns the correct data
        ResponseEntity<List<Status>> output = statusService.getAllStatuses();
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void when_getStatusByID_then_return_correct_status() {
        // mock the return of getStatusById from StatusRepository

        Status status = new Status();
        status.setId(1);
        status.setName("IN_ITERATION");

        ResponseEntity<Status> expected = new ResponseEntity<Status>(status, HttpStatus.OK);
        when(statusRepository.findById(1)).thenReturn(Optional.of(status));

        // check to see if the method returns the correct data
        ResponseEntity<Status> output = statusService.getStatusById(1);
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void when_getStatusByID_bad_id_then_return_bad_request() {
        // no need to mock anything since statusRepository.findById() will return an
        // empty optional
        ResponseEntity<Status> expected = new ResponseEntity<Status>(HttpStatus.BAD_REQUEST);
        when(statusRepository.findById(anyInt())).thenReturn(Optional.empty());

        // check to see if the method returns the correct data
        ResponseEntity<Status> output = statusService.getStatusById(-1);
        assertThat(output).isEqualTo(expected);
    }

}
