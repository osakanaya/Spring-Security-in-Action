package uk.me.uohiro.ssia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/a")
	public String getEndpointA() {
		return "Works!";
	}
	
	@PostMapping("/a")
	public String postEndpointA() {
		return "Works!";
	}
	
	@GetMapping("/a/b")
	public String getEndpointB() {
		return "Works!";
	}
	
	@GetMapping("/a/b/c")
	public String getEndpointC() {
		return "Works!";
	}
	
}
