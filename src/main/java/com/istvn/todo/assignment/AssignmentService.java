package com.istvn.todo.assignment;

import java.util.List;

public interface AssignmentService {
	List<String> createAssignments(SaveAssignmentDTO assignmentDTOs);

	AssignmentDTO getAssignment(String id);

	List<AssignmentDTO> listAssignments();
	
	List<AssignmentDTO> listAssignmentsByTaskId(String taskId);

	List<AssignmentDTO> listAssignmentsByEmployeeId(String employeeId);


	List<String> deleteAssignments(List<String> ids);

	String deleteAssignment(String id);
}
