package com.istvn.todo.employee;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Data
public class EmployeeDTO {
	public EmployeeDTO() {
		super();
	}
	private String id;
	private String createdAt;
	private String updatedAt;
	private String name;
	private LocalDate birthDate;
}
