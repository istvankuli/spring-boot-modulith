package com.istvn.todo.gateway.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.istvn.todo.assignment.AssignmentDTO;
import com.istvn.todo.assignment.AssignmentService;
import com.istvn.todo.assignment.SaveAssignmentDTO;
import com.istvn.todo.assignment.exception.AssignmentNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(AssignmentController.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private SaveAssignmentDTO saveAssignmentDTO;
    private AssignmentDTO assignmentDTO;

    @BeforeEach
    void setUp() {
        saveAssignmentDTO = new SaveAssignmentDTO();
        saveAssignmentDTO.setEmployeeIds(List.of("1"));
        saveAssignmentDTO.setTaskId("1");
        
        assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId("1");
    }

    @Test
    void createAssignment() throws Exception {
        List<String> response = Arrays.asList("Assignment created");
        when(assignmentService.createAssignments(any(SaveAssignmentDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveAssignmentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]").value("Assignment created"));
    }

    @Test
    void createAssignment_invalidInput() throws Exception {
        SaveAssignmentDTO invalidSaveAssignmentDTO = new SaveAssignmentDTO(); // Missing required fields

        mockMvc.perform(post("/api/v1/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSaveAssignmentDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAssignment() throws Exception {
        String response = "1";
        when(assignmentService.deleteAssignment("1")).thenReturn(response);

        mockMvc.perform(delete("/api/v1/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("1"));
    }

    @Test
    void deleteAssignment_notFound() throws Exception {
        when(assignmentService.deleteAssignment("1")).thenThrow(new AssignmentNotFoundException("1"));

        mockMvc.perform(delete("/api/v1/assignments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Assignment not found with id: 1"));
    }

    @Test
    void getAssignment() throws Exception {
        when(assignmentService.getAssignment("1")).thenReturn(assignmentDTO);

        mockMvc.perform(get("/api/v1/assignments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(assignmentDTO.getId()));
                // Add more assertions for other fields
    }

    @Test
    void getAssignment_notFound() throws Exception {
        when(assignmentService.getAssignment("1")).thenThrow(new AssignmentNotFoundException("1"));

        mockMvc.perform(get("/api/v1/assignments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Assignment not found with id: 1"));
    }

    @Test
    void listAssignments() throws Exception {
        List<AssignmentDTO> assignments = Arrays.asList(assignmentDTO);
        when(assignmentService.listAssignments()).thenReturn(assignments);

        mockMvc.perform(get("/api/v1/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(assignmentDTO.getId()));
                // Add more assertions for other fields
    }

    @Test
    void listAssignments_empty() throws Exception {
        when(assignmentService.listAssignments()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/assignments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }
    
    @Test
    void listAssignments_methodNotAllowed() throws Exception {
        mockMvc.perform(delete("/api/v1/assignments"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("$.data").isEmpty());
    }
}

