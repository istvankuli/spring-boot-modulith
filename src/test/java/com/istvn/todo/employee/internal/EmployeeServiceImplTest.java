package com.istvn.todo.employee.internal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeDeletedEvent;
import com.istvn.todo.employee.exception.EmployeeNotFoundException;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ApplicationEventPublisher events;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize some dummy data for tests
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId("123");
        employeeDTO.setName("John Doe");

        employee = new Employee();
        employee.setId("123");
        employee.setName("John Doe");
    }

    @Test
    void createEmployee_whenValidEmployeeDTO_thenReturnCreatedEmployeeDTO() {
        // Arrange
        when(employeeMapper.toEmployeeEnity(employeeDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toEmployeeDTO(employee)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        // Assert
        assertNotNull(createdEmployee);
        assertEquals("123", createdEmployee.getId());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployee_whenEmployeeExists_thenReturnDeletedEmployeeIdAndPublishEvent() {
        // Arrange
        String employeeId = "123";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        String deletedEmployeeId = employeeService.deleteEmployee(employeeId);

        // Assert
        assertEquals(employeeId, deletedEmployeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
        verify(events, times(1)).publishEvent(any(EmployeeDeletedEvent.class));
    }

    @Test
    void deleteEmployee_whenEmployeeNotExists_thenThrowEmployeeNotFoundException() {
        // Arrange
        String employeeId = "456";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
        verify(employeeRepository, never()).deleteById(employeeId);
        verify(events, never()).publishEvent(any(EmployeeDeletedEvent.class));
    }

    @Test
    void getEmployee_whenEmployeeExists_thenReturnEmployeeDTO() {
        // Arrange
        String employeeId = "123";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeDTO(employee)).thenReturn(employeeDTO);

        // Act
        EmployeeDTO foundEmployee = employeeService.getEmployee(employeeId);

        // Assert
        assertNotNull(foundEmployee);
        assertEquals("123", foundEmployee.getId());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void getEmployee_whenEmployeeNotExists_thenThrowEmployeeNotFoundException() {
        // Arrange
        String employeeId = "456";
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeMapper, never()).toEmployeeDTO(any());
    }

    @Test
    void listEmployees_whenEmployeesExist_thenReturnListOfEmployeeDTOs() {
        // Arrange
        List<Employee> employees = List.of(employee);
        List<EmployeeDTO> employeeDTOs = List.of(employeeDTO);
        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.toEmployeeDTOList(employees)).thenReturn(employeeDTOs);

        // Act
        List<EmployeeDTO> allEmployees = employeeService.listEmployees();

        // Assert
        assertNotNull(allEmployees);
        assertEquals(1, allEmployees.size());
        verify(employeeRepository, times(1)).findAll();
    }
}

