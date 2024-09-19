package com.istvn.todo.assignment;

import java.util.List;

public interface AssignmentService {
	List<String> createAssignments(SaveAssignmentDTO assignmentDTOs);

	AssignmentDTO getAssignment(String id);

	List<AssignmentDTO> listAssignments();

	List<String> deleteAssignments(List<String> ids);
}
