package com.istvn.todo.assignment.internal;

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

import com.istvn.todo.assignment.AssignmentDTO;
import com.istvn.todo.assignment.SaveAssignmentDTO;
import com.istvn.todo.assignment.exception.AssignmentNotFoundException;
import com.istvn.todo.assignment.exception.AssignmentNotValidException;
import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeService;
import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskService;

class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private AssignmentMapper assignmentMapper;

    @Mock
    private TaskService taskService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    private SaveAssignmentDTO saveAssignmentDTO;
    private Assignment assignment;
    private AssignmentDTO assignmentDTO;
    private TaskDTO taskDTO;
    private EmployeeDTO employeeDTO;
    private EmployeeDTO employeeDTO2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize dummy data for tests
        saveAssignmentDTO = new SaveAssignmentDTO();
        saveAssignmentDTO.setTaskId("task1");
        saveAssignmentDTO.setEmployeeIds(List.of("emp1", "emp2"));

        taskDTO = new TaskDTO();
        taskDTO.setId("task1");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setId("emp1");

        employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setId("emp2");
        
        assignment = new Assignment("task1", "emp1");
        assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId("assignment1");
    }

    @Test
    void createAssignments_whenTaskAndEmployeesExist_thenReturnListOfEmployeeIds() {
        // Arrange
        when(taskService.getTask("task1")).thenReturn(taskDTO);
        when(employeeService.getEmployee("emp1")).thenReturn(employeeDTO);
        when(employeeService.getEmployee("emp2")).thenReturn(employeeDTO2);

        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        // Act
        List<String> result = assignmentService.createAssignments(saveAssignmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(assignmentRepository, times(2)).save(any(Assignment.class));
    }

    @Test
    void createAssignments_whenTaskDoesNotExist_thenThrowAssignmentNotValidException() {
        // Arrange
        when(taskService.getTask("task1")).thenReturn(null);

        // Act & Assert
        assertThrows(AssignmentNotValidException.class, () -> assignmentService.createAssignments(saveAssignmentDTO));
        verify(assignmentRepository, never()).save(any(Assignment.class));
    }

    @Test
    void createAssignments_whenEmployeeDoesNotExist_thenThrowAssignmentNotValidException() {
        // Arrange
        when(taskService.getTask("task1")).thenReturn(taskDTO);
        when(employeeService.getEmployee("emp2")).thenReturn(null);

        // Act & Assert
        assertThrows(AssignmentNotValidException.class, () -> assignmentService.createAssignments(saveAssignmentDTO));
        verify(assignmentRepository, never()).save(any(Assignment.class));
    }

    @Test
    void getAssignment_whenAssignmentExists_thenReturnAssignmentDTO() {
        // Arrange
        when(assignmentRepository.findById("assignment1")).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toAssignmentDTO(assignment)).thenReturn(assignmentDTO);

        // Act
        AssignmentDTO result = assignmentService.getAssignment("assignment1");

        // Assert
        assertNotNull(result);
        assertEquals("assignment1", result.getId());
        verify(assignmentRepository, times(1)).findById("assignment1");
    }

    @Test
    void getAssignment_whenAssignmentDoesNotExist_thenThrowAssignmentNotFoundException() {
        // Arrange
        when(assignmentRepository.findById("assignment1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AssignmentNotFoundException.class, () -> assignmentService.getAssignment("assignment1"));
        verify(assignmentRepository, times(1)).findById("assignment1");
    }

    @Test
    void listAssignments_whenAssignmentsExist_thenReturnListOfAssignmentDTOs() {
        // Arrange
        List<Assignment> assignments = List.of(assignment);
        List<AssignmentDTO> assignmentDTOs = List.of(assignmentDTO);
        when(assignmentRepository.findAll()).thenReturn(assignments);
        when(assignmentMapper.toAssignmentDTOList(assignments)).thenReturn(assignmentDTOs);

        // Act
        List<AssignmentDTO> result = assignmentService.listAssignments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository, times(1)).findAll();
    }

    @Test
    void deleteAssignments_whenAssignmentsExist_thenReturnListOfDeletedIds() {
        // Arrange
    	assignment.setId("assignment1");
        when(assignmentRepository.findById("assignment1")).thenReturn(Optional.of(assignment));

        // Act
        List<String> result = assignmentService.deleteAssignments(List.of("assignment1"));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository, times(1)).deleteById("assignment1");
    }

    @Test
    void deleteAssignments_whenAssignmentDoesNotExist_thenThrowAssignmentNotFoundException() {
        // Arrange
        when(assignmentRepository.findById("assignment1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AssignmentNotFoundException.class, () -> assignmentService.deleteAssignments(List.of("assignment1")));
        verify(assignmentRepository, never()).deleteById("assignment1");
    }

    @Test
    void listAssignmentsByTaskId_whenAssignmentsExist_thenReturnListOfAssignmentDTOs() {
        // Arrange
        List<Assignment> assignments = List.of(assignment);
        List<AssignmentDTO> assignmentDTOs = List.of(assignmentDTO);
        when(assignmentRepository.findByTaskId("task1")).thenReturn(assignments);
        when(assignmentMapper.toAssignmentDTOList(assignments)).thenReturn(assignmentDTOs);

        // Act
        List<AssignmentDTO> result = assignmentService.listAssignmentsByTaskId("task1");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository, times(1)).findByTaskId("task1");
    }

    @Test
    void listAssignmentsByEmployeeId_whenAssignmentsExist_thenReturnListOfAssignmentDTOs() {
        // Arrange
        List<Assignment> assignments = List.of(assignment);
        List<AssignmentDTO> assignmentDTOs = List.of(assignmentDTO);
        when(assignmentRepository.findByEmployeeId("emp1")).thenReturn(assignments);
        when(assignmentMapper.toAssignmentDTOList(assignments)).thenReturn(assignmentDTOs);

        // Act
        List<AssignmentDTO> result = assignmentService.listAssignmentsByEmployeeId("emp1");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository, times(1)).findByEmployeeId("emp1");
    }
}
