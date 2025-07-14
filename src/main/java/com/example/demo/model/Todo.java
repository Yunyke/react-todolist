package com.example.demo.model;

import com.example.demo.repository.TodoRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String task;
	
	@Column(nullable = false)
	private boolean completed;
}
