package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.model.Document;
import uk.me.uohiro.ssia.services.DocumentService;

@SpringBootTest
public class MainTests {

	@Autowired
	private DocumentService documentService;
	
	@Test
	public void testDocumentServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> documentService.getDocument("abc123"));
	}
	
	@Test
	@WithMockUser(username = "emma", roles = "manager")
	public void testDocumentServiceWithManagerRole() {
		assertThrows(AccessDeniedException.class, () -> documentService.getDocument("abc123"));
	}
	
	@Test
	@WithMockUser(username = "emma", roles = "manager")
	public void testDocumentServiceWithManagerRoleForOwnUserDocument() {
		
		Document result = documentService.getDocument("asd555");
		Document expected = new Document("emma");
		
		assertEquals(expected, result);
	}
	
	@Test
	@WithMockUser(username = "natalie", roles = "admin")
	public void testDocumentServiceWithAdminRole() {

		Document result = documentService.getDocument("asd555");
		Document expected = new Document("emma");
		
		assertEquals(expected, result);
	}
}
