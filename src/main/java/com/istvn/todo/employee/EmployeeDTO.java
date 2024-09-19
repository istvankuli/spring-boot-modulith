package com.istvn.todo.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeDTO {
	public EmployeeDTO() {
		super();
	}
	private String id;
	private String createdAt;
	private String updatedAt;
	
	@NotNull
	private String name;
	
	@NotNull
	private LocalDate birthDate;
}
