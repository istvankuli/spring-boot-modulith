package com.istvn.todo.assignment;

import lombok.Value;

@Value
public class AssignmentDTO {
	private String id;
	private String createdAt;
	private String updatedAt;
	private String employeeId;
	private String taskId;
}
