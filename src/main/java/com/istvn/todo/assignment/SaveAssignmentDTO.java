package com.istvn.todo.assignment;

import java.util.List;

import lombok.Data;

@Data
public class SaveAssignmentDTO {

	private List<String> employeeIds;
	private String taskId;
}
