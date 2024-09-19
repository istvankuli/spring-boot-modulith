package com.istvn.todo.assignment.exception;

public class AssignmentNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5549707573677910993L;

	public AssignmentNotValidException() {
        super("Error while creating assignment!");
    }
	
	public AssignmentNotValidException(String message) {
        super(message);
    }

    public AssignmentNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
