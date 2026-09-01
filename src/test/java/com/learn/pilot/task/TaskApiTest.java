package com.learn.pilot.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createListGetUpdateAndDeleteTask() throws Exception {
		MvcResult created = mockMvc.perform(post("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"title":"Write unit tests","description":"Cover CRUD","status":"TODO"}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.title", is("Write unit tests")))
				.andExpect(jsonPath("$.status", is("TODO")))
				.andReturn();

		String body = created.getResponse().getContentAsString();
		String id = body.replaceAll(".*\"id\"\\s*:\\s*(\\d+).*", "$1");

		mockMvc.perform(get("/api/tasks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.id == " + id + ")]").exists());

		mockMvc.perform(get("/api/tasks/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Write unit tests")));

		mockMvc.perform(put("/api/tasks/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"title":"Write better tests","description":"CRUD + validation","status":"IN_PROGRESS"}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Write better tests")))
				.andExpect(jsonPath("$.status", is("IN_PROGRESS")));

		mockMvc.perform(patch("/api/tasks/{id}/status", id)
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"status":"DONE"}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("DONE")));

		mockMvc.perform(get("/api/tasks").param("status", "DONE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.id == " + Long.parseLong(id) + ")].status", hasSize(1)));

		mockMvc.perform(delete("/api/tasks/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/tasks/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString("Task not found")));
	}

	@Test
	void createRejectsBlankTitle() throws Exception {
		mockMvc.perform(post("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{"title":"  "}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Validation failed")));
	}
}
