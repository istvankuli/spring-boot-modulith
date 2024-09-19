package com.istvn.todo.task;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskDTO {
	private String id;
	private String createdAt;
	private String updatedAt;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
}
