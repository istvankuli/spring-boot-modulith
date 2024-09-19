package com.istvn.todo.assignment;

import java.util.List;

public interface AssignmentService {
	List<AssignmentDTO> createAssignments(List<AssignmentDTO> assignmentDTOs);

	AssignmentDTO getAssignment(String id);

	List<AssignmentDTO> listAssignments();

	List<String> deleteAssignments(List<String> ids);
}
