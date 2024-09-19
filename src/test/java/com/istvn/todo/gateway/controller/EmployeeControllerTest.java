package com.istvn.todo.gateway.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeService;
import com.istvn.todo.employee.exception.EmployeeNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId("1");
        employeeDTO.setName("John Doe");
        employeeDTO.setBirthDate(LocalDate.of(2000, 1, 1));
    }

    @Test
    void createEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(employeeDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(employeeDTO.getName()));
    }

    @Test
    void createEmployee_invalidInput() throws Exception {
        EmployeeDTO invalidEmployeeDTO = new EmployeeDTO(); // Missing required fields

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmployeeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployee("1")).thenReturn("Employee deleted");

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Employee deleted"));
    }

    @Test
    void deleteEmployee_notFound() throws Exception {
        when(employeeService.deleteEmployee("1")).thenThrow(new EmployeeNotFoundException("1"));

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Employee not found with id: 1"));
    }

    @Test
    void getEmployee() throws Exception {
        when(employeeService.getEmployee("1")).thenReturn(employeeDTO);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(employeeDTO.getId()))
                .andExpect(jsonPath("$.data.name").value(employeeDTO.getName()));
    }

    @Test
    void getEmployee_notFound() throws Exception {
        when(employeeService.getEmployee("1")).thenThrow(new EmployeeNotFoundException("1"));

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Employee not found with id: 1"));
    }

    @Test
    void listEmployees() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.listEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(employeeDTO.getId()))
                .andExpect(jsonPath("$.data[0].name").value(employeeDTO.getName()));
    }

    @Test
    void listEmployees_empty() throws Exception {
        when(employeeService.listEmployees()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
