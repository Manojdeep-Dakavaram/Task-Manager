package com.example.taskmanager.demo;

import com.example.taskmanager.demo.model.Task;

import com.example.taskmanager.demo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;

@SpringBootTest
@ActiveProfiles("test")
class TaskManagerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TaskRepository taskRepository;

	@Test
	public void testCreateTask() {
		// Arrange
		Date today = Calendar.getInstance().getTime();
		Task task = new Task("Test Task", "Test Description", "TO DO", today , "HIGH","user1@gmail.com");

		// Act
		Task savedTask = taskRepository.save(task);
		Task updatedtask = taskRepository.findById(savedTask.getId()).orElse(null);

		// Assert
		assertThat(savedTask).isNotNull();
		assertThat(updatedtask.getDueDate().toString()).isEqualTo(today.toString());
		assertThat(savedTask.getTitle()).isEqualTo("Test Task");
	}

}
