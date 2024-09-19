package com.istvn.todo.task.internal;

import java.util.List;

import org.mapstruct.Mapper;

import com.istvn.todo.task.TaskDTO;

@Mapper(componentModel = "spring")
public interface TaskMapper {
	Task toTaskEntity(TaskDTO taskDTO);

	List<Task> toTaskEntityList(List<TaskDTO> taskDTOs);
	
	TaskDTO toTaskDTO(Task task);
	
	List<TaskDTO> toTaskDTOList(List<Task> tasks);

}
