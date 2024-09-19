package com.istvn.todo.task.internal.exception;

import java.util.NoSuchElementException;

public class TaskNotFoundException extends NoSuchElementException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072646664176815194L;

	public TaskNotFoundException(String id) {
        super("Task not found with id: "+id);
    }

    public TaskNotFoundException(String id, Throwable cause) {
        super("Task not found with id: "+id, cause);
    }
}
