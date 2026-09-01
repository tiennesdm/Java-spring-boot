package com.learn.pilot.task;

import com.learn.pilot.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic yahan rehti hai — Controller sirf HTTP handle karta hai.
 * {@code @Service} is class ko Spring bean bana deta hai (dependency injection).
 */
@Service
@Transactional
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Transactional(readOnly = true)
	public List<TaskResponse> list(TaskStatus status) {
		List<Task> tasks = status == null
				? taskRepository.findAllByOrderByCreatedAtDesc()
				: taskRepository.findByStatusOrderByCreatedAtDesc(status);
		return tasks.stream().map(TaskResponse::from).toList();
	}

	@Transactional(readOnly = true)
	public TaskResponse get(Long id) {
		return TaskResponse.from(findOrThrow(id));
	}

	public TaskResponse create(TaskRequest request) {
		Task task = new Task(request.getTitle().trim(), request.getDescription(), request.getStatus());
		return TaskResponse.from(taskRepository.save(task));
	}

	public TaskResponse update(Long id, TaskRequest request) {
		Task task = findOrThrow(id);
		task.setTitle(request.getTitle().trim());
		task.setDescription(request.getDescription());
		if (request.getStatus() != null) {
			task.setStatus(request.getStatus());
		}
		return TaskResponse.from(taskRepository.save(task));
	}

	public TaskResponse updateStatus(Long id, TaskStatus status) {
		Task task = findOrThrow(id);
		task.setStatus(status);
		return TaskResponse.from(taskRepository.save(task));
	}

	public void delete(Long id) {
		if (!taskRepository.existsById(id)) {
			throw new ResourceNotFoundException("Task not found: " + id);
		}
		taskRepository.deleteById(id);
	}

	private Task findOrThrow(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
	}
}
