package com.learn.pilot.task;

import java.time.Instant;

public class TaskResponse {

	private Long id;
	private String title;
	private String description;
	private TaskStatus status;
	private Instant createdAt;

	public TaskResponse(Long id, String title, String description, TaskStatus status, Instant createdAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
	}

	public static TaskResponse from(Task task) {
		return new TaskResponse(
				task.getId(),
				task.getTitle(),
				task.getDescription(),
				task.getStatus(),
				task.getCreatedAt());
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
