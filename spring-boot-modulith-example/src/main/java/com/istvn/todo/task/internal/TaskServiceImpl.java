package com.istvn.todo.task.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.istvn.todo.task.TaskDTO;
import com.istvn.todo.task.TaskService;
import com.istvn.todo.task.internal.exception.TaskNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;

	private final TaskMapper taskMapper;

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
	 * Deletes a task by its ID if it exists in the repository. If the task with the
	 * given ID is not found, a TaskNotFoundException is thrown.
	 * 
	 * @param id The ID of the task to be deleted.
	 * @return The ID of the deleted task.
	 * @throws TaskNotFoundException if no task with the given ID exists.
	 */
	@Override
	public String deleteTask(String id) {
		Optional<Task> taskToDelete = taskRepository.findById(id);

		if (!taskToDelete.isPresent()) {
			throw new TaskNotFoundException("Task not found with the given id: " + id);
		}

		taskRepository.deleteById(id);

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
