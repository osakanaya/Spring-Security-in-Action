package uk.me.uohiro.ssia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.config.services.BookService;
import uk.me.uohiro.ssia.model.Employee;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/book/details/{name}")
	public Employee getDetails(@PathVariable String name) {
		return bookService.getBookDetails(name);
	}
}
