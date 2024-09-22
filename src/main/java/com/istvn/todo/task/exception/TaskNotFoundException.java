package com.istvn.todo.task.exception;

import java.util.NoSuchElementException;

public class TaskNotFoundException extends NoSuchElementException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072646664176815194L;

	public TaskNotFoundException(String id) {
        super("Task not found with id: "+id);
    }

}
