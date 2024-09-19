package com.istvn.todo.task;

import java.util.List;

public interface TaskService {
	TaskDTO createTask(TaskDTO taskDTO);
	
	String deleteTask(String id);
	
	TaskDTO getTask(String id);
	
	List<TaskDTO> listTasks();

}
