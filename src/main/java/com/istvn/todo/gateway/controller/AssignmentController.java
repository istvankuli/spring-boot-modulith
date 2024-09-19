package com.istvn.todo.gateway.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istvn.todo.assignment.AssignmentDTO;
import com.istvn.todo.assignment.AssignmentService;
import com.istvn.todo.assignment.SaveAssignmentDTO;
import com.istvn.todo.gateway.response.Response;
import com.istvn.todo.gateway.response.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AssignmentController {
	private final AssignmentService assignmentService;
	
	@PostMapping("/assignments")
	public Response<List<String>> createAssignment(@RequestBody @Valid SaveAssignmentDTO saveAssignmentDTO) {
		return new ResponseBuilder<List<String>>()
				.addData(assignmentService.createAssignments(saveAssignmentDTO))
				.build();
	}
	
	@DeleteMapping("/assignments/{id}")
	public Response<String> deleteAssignment(@PathVariable String id){
		return new ResponseBuilder<String>()
				.addData(assignmentService.deleteAssignment(id))
				.build();
	}
	
	@GetMapping("/assignments/{id}")
	public Response<AssignmentDTO> getAssignment(@PathVariable String id){
		return new ResponseBuilder<AssignmentDTO>()
				.addData(assignmentService.getAssignment(id))
				.build();
	}
	
	@GetMapping("/assignments")
	public Response<List<AssignmentDTO>> listAssignments(){
		return new ResponseBuilder<List<AssignmentDTO>>()
				.addData(assignmentService.listAssignments())
				.build();
	}
}
