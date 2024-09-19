package com.istvn.todo.gateway.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskService;
import com.istvn.todo.task.exception.TaskNotFoundException;

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

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO();
        taskDTO.setId("1");
        taskDTO.setTitle("Sample Task");
        taskDTO.setDescription("Description");
        taskDTO.setStartDate(LocalDate.of(2024, 1, 1));
        taskDTO.setEndDate(LocalDate.of(2024, 1, 2));
    }

    @Test
    void createTask() throws Exception {
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.data.title").value(taskDTO.getTitle()));
    }

    @Test
    void createTask_invalidInput() throws Exception {
        TaskDTO invalidTaskDTO = new TaskDTO(); // Missing required fields

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTaskDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTask() throws Exception {
        when(taskService.deleteTask("1")).thenReturn("Task deleted");

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Task deleted"));
    }

    @Test
    void deleteTask_notFound() throws Exception {
        when(taskService.deleteTask("1")).thenThrow(new TaskNotFoundException("1"));

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Task not found with id: 1"));
    }

    @Test
    void getTask() throws Exception {
        when(taskService.getTask("1")).thenReturn(taskDTO);

        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.data.title").value(taskDTO.getTitle()));
    }

    @Test
    void getTask_notFound() throws Exception {
        when(taskService.getTask("1")).thenThrow(new TaskNotFoundException("1"));

        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.message").value("Task not found with id: 1"));
    }

    @Test
    void listTasks() throws Exception {
        List<TaskDTO> tasks = Arrays.asList(taskDTO);
        when(taskService.listTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.data[0].title").value(taskDTO.getTitle()));
    }

    @Test
    void listTasks_empty() throws Exception {
        when(taskService.listTasks()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
