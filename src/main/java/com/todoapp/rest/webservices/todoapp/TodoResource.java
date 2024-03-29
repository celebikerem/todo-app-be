package com.todoapp.rest.webservices.todoapp;

import java.net.URI;
import java.util.List;

import javax.servlet.Servlet;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoResource {

	@Autowired
	private TodoHardcodedService todoService;
	
	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodos(@PathVariable String username, @PathVariable long id){
		return todoService.findById(id);
	}
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoService.findAll();
	}
	
	@PutMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		Todo todoUpdated = todoService.save(todo);
		
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}
	
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<Void> updateTodo(@PathVariable String username, @RequestBody Todo todo){
		Todo createdTodo = todoService.save(todo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/users/{username}/todos/{id}")
		public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
			Todo todo = todoService.deleteById(id);
			if(todo!=null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		}
	}

