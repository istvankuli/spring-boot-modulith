package com.istvn.todo.employee;

import java.time.LocalDate;

import lombok.Value;

@Value
public class EmployeeDTO {
	private String id;
	private String createdAt;
	private String updatedAt;
	private String name;
	private LocalDate birthDate;
}
