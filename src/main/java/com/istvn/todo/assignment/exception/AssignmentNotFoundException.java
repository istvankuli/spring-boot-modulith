package com.istvn.todo.assignment.exception;

import java.util.NoSuchElementException;

public class AssignmentNotFoundException extends NoSuchElementException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5726417247884499693L;

	public AssignmentNotFoundException(String id) {
        super("Assignment not found with id: "+id);
    }

    public AssignmentNotFoundException(String id, Throwable cause) {
        super("Assignment not found with id: "+id, cause);
    }
}
