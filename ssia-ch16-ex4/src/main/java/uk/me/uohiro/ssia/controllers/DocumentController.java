package uk.me.uohiro.ssia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.config.services.DocumentService;
import uk.me.uohiro.ssia.model.Document;

@RestController
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	@GetMapping("/documents/{code}")
	public Document getDetails(@PathVariable String code) {
		return documentService.getDocument(code);
	}
	
}
