package com.learn.pilot.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository.
 * Interface likho — Spring runtime pe implementation generate karta hai.
 * Method names se query ban jaati hai: findByStatus(...) → WHERE status = ?
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByStatusOrderByCreatedAtDesc(TaskStatus status);

	List<Task> findAllByOrderByCreatedAtDesc();
}
