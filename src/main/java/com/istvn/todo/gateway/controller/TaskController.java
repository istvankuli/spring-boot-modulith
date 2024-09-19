package com.istvn.todo.gateway.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istvn.todo.gateway.response.Response;
import com.istvn.todo.gateway.response.ResponseBuilder;
import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TaskController {
	private final TaskService taskService;
	
	@PostMapping("/tasks")
	public Response<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
		return new ResponseBuilder<TaskDTO>()
				.addData(taskService.createTask(taskDTO))
				.build();
	}
	
	@DeleteMapping("/tasks/{id}")
	public Response<String> deleteTask(@PathVariable String id){
		return new ResponseBuilder<String>()
				.addData(taskService.deleteTask(id))
				.build();
	}
	
	@GetMapping("/tasks/{id}")
	public Response<TaskDTO> getTask(@PathVariable String id){
		return new ResponseBuilder<TaskDTO>()
				.addData(taskService.getTask(id))
				.build();
	}
	
	@GetMapping("/tasks")
	public Response<List<TaskDTO>> listTasks(){
		return new ResponseBuilder<List<TaskDTO>>()
				.addData(taskService.listTasks())
				.build();
	}

}
