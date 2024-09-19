package com.istvn.todo.assignment;

import java.util.List;

import lombok.Value;

@Value
public class SaveAssignmentDTO {
	private List<String> employeeIds;
	private String taskId;
}
