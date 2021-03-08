package uk.me.uohiro.ssia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.services.NameService;

@RestController
public class HelloController {

	@Autowired
	private NameService nameService;
	
	@GetMapping("/hello1")
	public String hello1() {
		return "Hello, " + nameService.getName1();
	}
	
	@GetMapping("/hello2")
	public String hello2() {
		return "Hello, " + nameService.getName2();
	}
}
