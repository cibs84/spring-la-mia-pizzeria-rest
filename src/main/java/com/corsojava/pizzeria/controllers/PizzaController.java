package com.corsojava.pizzeria.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corsojava.pizzeria.models.Pizza;
import com.corsojava.pizzeria.repository.PizzaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/pizze")
public class PizzaController {
	
	@Autowired
	PizzaRepository pizzaRepository;
	
	@GetMapping
	public List<Pizza> index() {
		return pizzaRepository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Pizza> show(@PathVariable("id") Long id) {
		Optional<Pizza> res = pizzaRepository.findById(id);
		if (res.isPresent()) {
			return new ResponseEntity<Pizza>(res.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public Pizza create(@RequestBody Pizza pizza) {
		return pizzaRepository.save(pizza);
	}
	
	@PutMapping("{id}")
	@PostMapping("/update")
	public Pizza update(@RequestBody Pizza pizza,
			@PathVariable("id") Long id) {
		Pizza p = pizzaRepository.getReferenceById(id);
		p.setName(pizza.getName());
		return pizzaRepository.save(pizza);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		
		Optional<Pizza> pizzaFromDb = pizzaRepository.findById(id);
		if (pizzaFromDb.isPresent()) {
			pizzaRepository.deleteById(id);
			return new ResponseEntity<String>("Pizza deleted", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>("Pizza to deleted is not found", HttpStatus.NOT_FOUND);
		}
	}
}
