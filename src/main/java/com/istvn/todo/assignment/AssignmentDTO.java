package com.istvn.todo.assignment;

import lombok.Data;

@Data
public class AssignmentDTO {
	private String id;
	private String createdAt;
	private String updatedAt;
	private String employeeId;
	private String taskId;
}
