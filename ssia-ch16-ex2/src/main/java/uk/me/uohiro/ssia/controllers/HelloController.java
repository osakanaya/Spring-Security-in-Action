package uk.me.uohiro.ssia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.config.services.NameService;

@RestController
public class HelloController {

	@Autowired
	private NameService nameService;
	
	@GetMapping("/secret/names/{name}")
	public List<String> names(@PathVariable String name) {
		return nameService.getSecretNames(name);
	}
}
