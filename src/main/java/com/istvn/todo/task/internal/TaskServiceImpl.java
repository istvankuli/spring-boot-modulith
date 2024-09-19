package com.istvn.todo.task.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskDeletedEvent;
import com.istvn.todo.task.TaskService;
import com.istvn.todo.task.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;

	private final TaskMapper taskMapper;
	
    private final ApplicationEventPublisher events;


	/**
	 * Creates a new task by saving the provided TaskDTO to the repository. Converts
	 * the TaskDTO to a Task entity, saves it, and returns the saved task as a
	 * TaskDTO.
	 * 
	 * @param taskDTO The data transfer object containing the task details to be
	 *                created.
	 * @return The saved task as a TaskDTO.
	 */
	@Override
	public TaskDTO createTask(TaskDTO taskDTO) {
		Task savedTask = taskRepository.save(taskMapper.toTaskEntity(taskDTO));

		return taskMapper.toTaskDTO(savedTask);
	}

	/**
	 * Deletes a task by its ID and publishes a {@link TaskDeletedEvent}.
	 * This method attempts to find the task in the repository using the provided ID.
	 * If the task is not found, a {@link TaskNotFoundException} is thrown. 
	 * If the task is found, it is deleted from the repository, and a {@link TaskDeletedEvent} 
	 * is published to notify other components of the deletion.
	 * <p>
	 * The method is annotated with {@link Transactional}, ensuring that the deletion 
	 * and event publication are executed within the same transaction.
	 *
	 * @param id the unique identifier of the task to be deleted.
	 * @return the ID of the task that was deleted.
	 * @throws TaskNotFoundException if no task with the given ID is found.
	 */
	@Override
	@Transactional
	public String deleteTask(String id) {
		Optional<Task> taskToDelete = taskRepository.findById(id);

		if (!taskToDelete.isPresent()) {
			throw new TaskNotFoundException("Task not found with the given id: " + id);
		}

		taskRepository.deleteById(id);
		
		events.publishEvent(new TaskDeletedEvent(id));

		return id;
	}

	/**
	 * Finds a task by its ID and returns it as a TaskDTO. If no task is found with
	 * the given ID, a TaskNotFoundException is thrown.
	 * 
	 * @param id The ID of the task to be retrieved.
	 * @return The task as a TaskDTO.
	 * @throws TaskNotFoundException if no task with the given ID is found.
	 */
	@Override
	public TaskDTO getTask(String id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

		return taskMapper.toTaskDTO(task);
	}

	/**
	 * Retrieves all tasks from the repository and returns them as a list of
	 * TaskDTOs.
	 * 
	 * @return A list of TaskDTOs representing all tasks in the repository.
	 */
	@Override
	public List<TaskDTO> listTasks() {
		List<Task> tasks = taskRepository.findAll();

		return taskMapper.toTaskDTOList(tasks);
	}

}
