package com.istvn.todo.employee.internal;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeDeletedEvent;
import com.istvn.todo.employee.EmployeeService;
import com.istvn.todo.employee.internal.exception.EmployeeNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;
	
    private final ApplicationEventPublisher events;


	/**
	 * Creates a new employee entity from the provided {@link EmployeeDTO} object.
	 * This method maps the provided {@link EmployeeDTO} to an {@link Employee}
	 * entity, saves or processes it (if applicable), and then maps the resulting
	 * {@link Employee} back to a {@link EmployeeDTO}.
	 *
	 * @param employeeDTO the {@link EmployeeDTO} object containing the employee
	 *                    details to be created.
	 * @return the newly created {@link EmployeeDTO} object after the entity has
	 *         been successfully mapped and processed.
	 */
	@Override
	public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
		Employee employeeToCreate = employeeMapper.toEmployeeEnity(employeeDTO);

		return employeeMapper.toEmployeeDTO(employeeToCreate);
	}

	/**
	 * Deletes an employee by their ID and publishes an {@link EmployeeDeletedEvent}.
	 * This method first attempts to find the employee in the repository by the provided ID.
	 * If found, the employee is deleted, and an {@link EmployeeDeletedEvent} is published 
	 * to notify other parts of the system about the deletion. If the employee is not found, 
	 * an {@link EmployeeNotFoundException} is thrown.
	 * <p>
	 * The method is annotated with {@link Transactional}, ensuring that the deletion and 
	 * event publication occur within a single transaction.
	 *
	 * @param id the unique identifier of the employee to be deleted.
	 * @return the ID of the employee that was deleted.
	 * @throws EmployeeNotFoundException if no employee with the given ID is found.
	 */
	@Override
	@Transactional
	public String deleteEmployee(String id) {
		employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		employeeRepository.deleteById(id);

		//publish event
		events.publishEvent(new EmployeeDeletedEvent(id));
		
		return id;
	}

	/**
	 * Retrieves an employee by their ID. This method finds the employee by the
	 * provided ID and maps the retrieved {@link Employee} entity to an
	 * {@link EmployeeDTO}. If no employee is found, an
	 * {@link EmployeeNotFoundException} is thrown.
	 *
	 * @param id the unique identifier of the employee to be retrieved.
	 * @return the {@link EmployeeDTO} object representing the employee with the
	 *         given ID.
	 * @throws EmployeeNotFoundException if no employee with the given ID is found.
	 */
	@Override
	public EmployeeDTO getEmployee(String id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return employeeMapper.toEmployeeDTO(employee);
	}

	/**
	 * Retrieves all employees from the repository and returns them as a list of
	 * EmployeeDTOs.
	 * 
	 * @return A list of EmployeeDTOs representing all employees in the repository.
	 */
	@Override
	public List<EmployeeDTO> listEmployees() {
		List<Employee> employees = employeeRepository.findAll();

		return employeeMapper.toEmployeeDTOList(employees);
	}

}
