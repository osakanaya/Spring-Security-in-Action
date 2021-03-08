package uk.me.uohiro.ssia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.model.Document;
import uk.me.uohiro.ssia.repositories.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;
	
	@PostAuthorize("hasPermission(#code, 'document', 'ROLE_admin')")
	public Document getDocument(String code) {
		return documentRepository.findDocument(code);
	}
	
}
