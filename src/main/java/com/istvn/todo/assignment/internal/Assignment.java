package com.istvn.todo.assignment.internal;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Assignments")
public class Assignment {
	
	public Assignment(@NotNull String taskId, @NotNull String employeeId) {
		this.taskId = taskId;
		this.employeeId = employeeId;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private @CreationTimestamp Instant createdAt;
	private @UpdateTimestamp Instant updatedAt;
	
	@NotNull
	private String taskId;

	@NotNull
	private String employeeId;
}
