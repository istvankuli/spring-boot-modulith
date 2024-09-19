package com.istvn.todo.employee.internal.exception;

import java.util.NoSuchElementException;

public class EmployeeNotFoundException extends NoSuchElementException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547842805815353680L;

	public EmployeeNotFoundException(String id) {
        super("Employee not found with id: "+id);
    }

    public EmployeeNotFoundException(String id, Throwable cause) {
        super("Employee not found with id: "+id, cause);
    }
}
