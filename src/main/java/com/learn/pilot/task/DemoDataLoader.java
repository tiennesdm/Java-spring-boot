package com.learn.pilot.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * App start hote hi 3 sample tasks insert karta hai — UI/API turant try karne ke liye.
 */
@Component
@Profile("!test")
public class DemoDataLoader implements CommandLineRunner {

	private final TaskRepository taskRepository;

	public DemoDataLoader(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public void run(String... args) {
		if (taskRepository.count() > 0) {
			return;
		}
		taskRepository.save(new Task("Spring Boot README padho", "Layers: Controller → Service → Repository", TaskStatus.DONE));
		taskRepository.save(new Task("Pehla REST call", "GET /api/tasks browser ya curl se", TaskStatus.IN_PROGRESS));
		taskRepository.save(new Task("Naya task create karo", "POST JSON body ke saath", TaskStatus.TODO));
	}
}
