package com.istvn.todo.task;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDTO {
	private String id;
	private String createdAt;
	private String updatedAt;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;
}
