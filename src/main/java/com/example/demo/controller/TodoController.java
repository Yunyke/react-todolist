package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("react/todos")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {

	@Autowired
	private TodoRepository todoRepository;

	@GetMapping
	public List<Todo> getAllTodos() {

		return todoRepository.findAll();
	}

	@PostMapping
	public Todo createTodo(@RequestBody Todo todo) {
		return todoRepository.save(todo);
	}

	@PutMapping("/{id}")
	public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updateTodo) {
		Todo existingTodo = todoRepository.findById(id).orElse(null);

		if (existingTodo == null) {
			throw new RuntimeException("Todo not found with id:" + id);
		}

		existingTodo.setTask(updateTodo.getTask());
		existingTodo.setCompleted(updateTodo.isCompleted());
		return todoRepository.save(existingTodo);
	}

	@DeleteMapping("/{id}")
	public void deleteTodo(@PathVariable Long id) {
		todoRepository.deleteById(id);
	}
}