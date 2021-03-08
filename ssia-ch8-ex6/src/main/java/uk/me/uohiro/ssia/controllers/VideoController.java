package uk.me.uohiro.ssia.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

	@GetMapping("/video/{country}/{language}")
	public String video(@PathVariable String country, @PathVariable String language) {
		return "Video allowed for country = " + country + ", language = " + language;
	}
}
