package com.istvn.todo.assignment.internal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
	List<Assignment> findByTaskId(String taskId);
	
	List<Assignment> findByEmployeeId(String employeeId);
}
