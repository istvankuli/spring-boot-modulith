package com.istvn.todo.task;

import java.time.LocalDate;

import lombok.Value;

@Value
public class TaskDTO {
	private String id;
	private String createdAt;
	private String updatedAt;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
}
