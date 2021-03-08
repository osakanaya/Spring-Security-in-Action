package uk.me.uohiro.ssia.repositories;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import uk.me.uohiro.ssia.model.Document;

@Repository
public class DocumentRepository {

	private Map<String, Document> documents = new HashMap<String, Document>();
	
	public Document findDocument(String code) {
		return documents.get(code);
	}
	
	@PostConstruct
	public void populateDocuments() {
		documents.put("abc123", new Document("natalie"));
		documents.put("qwe123", new Document("natalie"));
		documents.put("asd555", new Document("emma"));
	}
	
}
