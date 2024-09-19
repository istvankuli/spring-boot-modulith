package com.istvn.todo.gateway.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istvn.todo.employee.EmployeeDTO;
import com.istvn.todo.employee.EmployeeService;
import com.istvn.todo.gateway.response.Response;
import com.istvn.todo.gateway.response.ResponseBuilder;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EmployeeController {
	private final EmployeeService employeeService;

	@PostMapping("/employees")
	public Response<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		return new ResponseBuilder<EmployeeDTO>()
				.addData(employeeService.createEmployee(employeeDTO))
				.build();
	}
	
	@DeleteMapping("/employees/{id}")
	public Response<String> deleteEmployee(@PathVariable String id){
		return new ResponseBuilder<String>()
				.addData(employeeService.deleteEmployee(id))
				.build();
	}
	
	@GetMapping("/employees/{id}")
	public Response<EmployeeDTO> getEmployee(@PathVariable String id){
		return new ResponseBuilder<EmployeeDTO>()
				.addData(employeeService.getEmployee(id))
				.build();
	}
	
	@GetMapping("/employees")
	public Response<List<EmployeeDTO>> listEmployees(){
		return new ResponseBuilder<List<EmployeeDTO>>()
				.addData(employeeService.listEmployees())
				.build();
	}
}
