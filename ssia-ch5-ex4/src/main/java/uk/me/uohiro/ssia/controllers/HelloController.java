package uk.me.uohiro.ssia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/read")
	public String read() {
		return "read.html";
	}
	
	@GetMapping("/none")
	public String none() {
		return "none.html";
	}

}
