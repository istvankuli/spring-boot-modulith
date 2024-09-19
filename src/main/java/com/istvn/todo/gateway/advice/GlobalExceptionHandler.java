package com.istvn.todo.gateway.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.istvn.todo.assignment.exception.AssignmentNotFoundException;
import com.istvn.todo.employee.exception.EmployeeNotFoundException;
import com.istvn.todo.gateway.response.Response;
import com.istvn.todo.gateway.response.ResponseBuilder;
import com.istvn.todo.gateway.response.ResponseError;
import com.istvn.todo.task.exception.TaskNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { NoResourceFoundException.class, AssignmentNotFoundException.class, TaskNotFoundException.class, EmployeeNotFoundException.class, NoSuchElementException.class })
	public ResponseEntity<Object> handleNoResourceFoundException(Exception e) {
		return new ResponseEntity<>(
				new ResponseBuilder<>().fail("404").error(new ResponseError("404",
						e.getMessage())).build(),
				HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(value = { MethodNotAllowedException.class })
	public ResponseEntity<Object> handleMethodNotAllowedException(Exception e) {
		return new ResponseEntity<>(
				new ResponseBuilder<>().fail("405").error(new ResponseError("405",
						e.getMessage())).build(),
				HttpStatus.METHOD_NOT_ALLOWED);

	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingPathVariableException.class })
	public ResponseEntity<Object> handleMethodArgumentNotValidException(Exception e) {
		return new ResponseEntity<>(
				new ResponseBuilder<>().fail("400").error(new ResponseError("400",
						e.getMessage())).build(),
				HttpStatus.BAD_REQUEST);

	}
}
