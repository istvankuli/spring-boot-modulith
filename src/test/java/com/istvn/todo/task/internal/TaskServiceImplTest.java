package com.istvn.todo.task.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskDeletedEvent;
import com.istvn.todo.task.exception.TaskNotFoundException;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private ApplicationEventPublisher events;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskDTO taskDTO;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize dummy data for tests
        taskDTO = new TaskDTO();
        taskDTO.setId("123");
        taskDTO.setTitle("Sample Task");

        task = new Task();
        task.setId("123");
        task.setTitle("Sample Task");
    }

    @Test
    void createTask_whenValidTaskDTO_thenReturnCreatedTaskDTO() {
        // Arrange
        when(taskMapper.toTaskEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        // Act
        TaskDTO createdTask = taskService.createTask(taskDTO);

        // Assert
        assertNotNull(createdTask);
        assertEquals("123", createdTask.getId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deleteTask_whenTaskExists_thenReturnDeletedTaskIdAndPublishEvent() {
        // Arrange
        String taskId = "123";
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        String deletedTaskId = taskService.deleteTask(taskId);

        // Assert
        assertEquals(taskId, deletedTaskId);
        verify(taskRepository, times(1)).deleteById(taskId);
        verify(events, times(1)).publishEvent(any(TaskDeletedEvent.class));
    }

    @Test
    void deleteTask_whenTaskNotExists_thenThrowTaskNotFoundException() {
        // Arrange
        String taskId = "456";
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository, never()).deleteById(taskId);
        verify(events, never()).publishEvent(any(TaskDeletedEvent.class));
    }

    @Test
    void getTask_whenTaskExists_thenReturnTaskDTO() {
        // Arrange
        String taskId = "123";
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskDTO(task)).thenReturn(taskDTO);

        // Act
        TaskDTO foundTask = taskService.getTask(taskId);

        // Assert
        assertNotNull(foundTask);
        assertEquals("123", foundTask.getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTask_whenTaskNotExists_thenThrowTaskNotFoundException() {
        // Arrange
        String taskId = "456";
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskMapper, never()).toTaskDTO(any());
    }

    @Test
    void listTasks_whenTasksExist_thenReturnListOfTaskDTOs() {
        // Arrange
        List<Task> tasks = List.of(task);
        List<TaskDTO> taskDTOs = List.of(taskDTO);
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toTaskDTOList(tasks)).thenReturn(taskDTOs);

        // Act
        List<TaskDTO> allTasks = taskService.listTasks();

        // Assert
        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());
        verify(taskRepository, times(1)).findAll();
    }
}
