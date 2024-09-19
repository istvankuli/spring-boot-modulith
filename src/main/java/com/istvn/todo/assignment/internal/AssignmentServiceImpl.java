package com.istvn.todo.assignment.internal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istvn.todo.assignment.AssignmentDTO;
import com.istvn.todo.assignment.AssignmentService;
import com.istvn.todo.assignment.SaveAssignmentDTO;
import com.istvn.todo.assignment.exception.AssignmentNotFoundException;
import com.istvn.todo.assignment.exception.AssignmentNotValidException;
import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeDeletedEvent;
import com.istvn.todo.employee.EmployeeService;
import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskDeletedEvent;
import com.istvn.todo.task.TaskService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
	private final AssignmentRepository assignmentRepository;

	private final AssignmentMapper assignmentMapper;

	private final TaskService taskService;

	private final EmployeeService employeeService;

	/**
	 * Saves a list of assignments based on the provided SaveAssignmentDTO. Checks
	 * if the specified task exists and if each employee in the list exists. If the
	 * task or any employee does not exist, an AssignmentNotValidException is
	 * thrown. For each valid assignment, it is saved to the repository, and the
	 * employee ID is added to the result list.
	 * 
	 * @param saveAssignmentDTO The DTO containing the task ID and a list of
	 *                          employee IDs to be assigned to the task.
	 * @return A list of employee IDs for which assignments were successfully saved.
	 * @throws AssignmentNotValidException if the task does not exist or if any
	 *                                     employee does not exist.
	 */
	@Override
	@Transactional
	public List<String> createAssignments(SaveAssignmentDTO saveAssignmentDTO) {
		List<String> employeeIds = saveAssignmentDTO.getEmployeeIds();
		String taskId = saveAssignmentDTO.getTaskId();

		List<String> result = new ArrayList<>();

		// check if task exists with the id
		if (taskId.length() < 1 || !checkTaskExists(taskId)) {
			throw new AssignmentNotValidException("The task does not exist!");
		}

		// check if employees exist
		for (String employeeId : employeeIds) {
			// if no throw error
			if (employeeId.length() < 1 || !checkEmployeeExists(employeeId)) {
				throw new AssignmentNotValidException("Employee does not exist!");
			}
			// if yes, create assignment, save to the repository and add the id to the
			// result list
			Assignment assignment = new Assignment(taskId, employeeId);

			result.add(assignmentRepository.save(assignment).getEmployeeId());
		}

		return result;
	}

	/**
	 * Retrieves an assignment by its ID and returns it as an AssignmentDTO. If no
	 * assignment is found with the given ID, an AssignmentNotFoundException is
	 * thrown.
	 * 
	 * @param id The ID of the assignment to be retrieved.
	 * @return The assignment as an AssignmentDTO.
	 * @throws AssignmentNotFoundException if no assignment with the given ID is
	 *                                     found.
	 */
	@Override
	public AssignmentDTO getAssignment(String id) {
		Assignment assignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new AssignmentNotFoundException("Assignment does not exist with id: " + id));

		return assignmentMapper.toAssignmentDTO(assignment);
	}

	/**
	 * Retrieves all assignments from the repository and returns them as a list of
	 * AssignmentDTOs.
	 * 
	 * @return A list of AssignmentDTOs representing all assignments in the
	 *         repository.
	 */
	@Override
	public List<AssignmentDTO> listAssignments() {
		List<Assignment> assignments = assignmentRepository.findAll();

		return assignmentMapper.toAssignmentDTOList(assignments);
	}

	/**
	 * Deletes an assignment by its ID if it exists in the repository. If no
	 * assignment is found with the given ID, an AssignmentNotFoundException is
	 * thrown.
	 * 
	 * @param List of IDs of the assignments to be deleted.
	 * @return A list of IDs of deleted assignments.
	 * @throws AssignmentNotFoundException if no assignment with the given ID is
	 *                                     found.
	 */
	@Override
	@Transactional
	public List<String> deleteAssignments(List<String> ids) {
		List<String> result = new ArrayList<>();

		// Loop through the ids we want to delete
		for (String id : ids) {
			// If they exist delete them and add the to the results list
			Assignment assignment = assignmentRepository.findById(id)
					.orElseThrow(() -> new AssignmentNotFoundException(id));

			assignmentRepository.deleteById(assignment.getId());

			result.add(id);
		}

		return result;
	}

	/**
	 * Checks if a task with the specified ID exists by attempting to retrieve it.
	 * Returns true if the task is found, otherwise false.
	 * 
	 * @param id The ID of the task to check.
	 * @return {@code true} if a task with the given ID exists; {@code false}
	 *         otherwise.
	 */
	private boolean checkTaskExists(String id) {
		TaskDTO taskDTO = taskService.getTask(id);

		if (taskDTO != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a task with the specified ID exists by attempting to retrieve it.
	 * Returns true if the task is found, otherwise false.
	 * 
	 * @param id The ID of the task to check.
	 * @return {@code true} if a task with the given ID exists; {@code false}
	 *         otherwise.
	 */
	private boolean checkEmployeeExists(String id) {
		EmployeeDTO employeeDTO = employeeService.getEmployee(id);

		if (employeeDTO != null) {
			return true;
		}
		return false;
	}

	/**
	 * Retrieves a list of assignments by the given task ID. This method queries the
	 * repository for all assignments associated with the specified task ID, and
	 * maps the resulting {@link Assignment} entities to a list of
	 * {@link AssignmentDTO} objects.
	 *
	 * @param taskId the unique identifier of the task whose assignments are to be
	 *               retrieved.
	 * @return a list of {@link AssignmentDTO} objects corresponding to the
	 *         assignments of the specified task.
	 */
	@Override
	public List<AssignmentDTO> listAssignmentsByTaskId(String taskId) {
		List<Assignment> assignments = assignmentRepository.findByTaskId(taskId);

		return assignmentMapper.toAssignmentDTOList(assignments);
	}

	/**
	 * Retrieves a list of assignments by the given employee ID. This method queries
	 * the repository for all assignments associated with the specified employee ID,
	 * and maps the resulting {@link Assignment} entities to a list of
	 * {@link AssignmentDTO} objects.
	 *
	 * @param employeeId the unique identifier of the employee whose assignments are
	 *                   to be retrieved.
	 * @return a list of {@link AssignmentDTO} objects corresponding to the
	 *         assignments of the specified employee.
	 */
	@Override
	public List<AssignmentDTO> listAssignmentsByEmployeeId(String employeeId) {
		List<Assignment> assignments = assignmentRepository.findByEmployeeId(employeeId);

		return assignmentMapper.toAssignmentDTOList(assignments);
	}

	@ApplicationModuleListener
	void on(TaskDeletedEvent event) {
		List<Assignment> assignments = assignmentMapper.toAssignmentEnityList(listAssignmentsByTaskId(event.taskId()));

		for (Assignment assignment : assignments) {
			assignmentRepository.deleteById(assignment.getId());
		}
	}

	@ApplicationModuleListener
	void on(EmployeeDeletedEvent event) {
		List<Assignment> assignments = assignmentMapper
				.toAssignmentEnityList(listAssignmentsByEmployeeId(event.employeeId()));

		for (Assignment assignment : assignments) {
			assignmentRepository.deleteById(assignment.getId());
		}
	}

	/**
	 * Deletes an assignment by its ID if it exists in the repository. If no
	 * assignment is found with the given ID, an AssignmentNotFoundException is
	 * thrown.
	 * 
	 * @param List of IDs of the assignments to be deleted.
	 * @return A list of IDs of deleted assignments.
	 * @throws AssignmentNotFoundException if no assignment with the given ID is
	 *                                     found.
	 */
	@Override
	public String deleteAssignment(String id) {
		Assignment assignment = assignmentRepository.findById(id)
				.orElseThrow(() -> new AssignmentNotFoundException(id));

		assignmentRepository.deleteById(assignment.getId());

		return assignment.getId();
	}

}
