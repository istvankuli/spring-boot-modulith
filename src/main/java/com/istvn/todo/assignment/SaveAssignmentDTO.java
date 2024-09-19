package com.istvn.todo.assignment;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveAssignmentDTO {
	@NotNull
	private List<String> employeeIds;
	
	@NotNull
	private String taskId;
}
