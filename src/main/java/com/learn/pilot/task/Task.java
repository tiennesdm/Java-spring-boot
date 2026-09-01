package com.learn.pilot.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * JPA Entity = database table ka Java mapping.
 * Table name: tasks
 */
@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 120)
	private String title;

	@Column(length = 1000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TaskStatus status = TaskStatus.TODO;

	@Column(nullable = false, updatable = false)
	private Instant createdAt = Instant.now();

	protected Task() {
		// JPA ko no-args constructor chahiye
	}

	public Task(String title, String description, TaskStatus status) {
		this.title = title;
		this.description = description;
		this.status = status == null ? TaskStatus.TODO : status;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
