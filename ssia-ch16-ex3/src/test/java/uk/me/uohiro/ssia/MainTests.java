package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.config.services.BookService;
import uk.me.uohiro.ssia.model.Employee;

@SpringBootTest
public class MainTests {

	@Autowired
	private BookService bookService;
	
	@Test
	public void testBookServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> bookService.getBookDetails("emma"));
	}
	
	@Test
	@WithMockUser(username = "emma")
	public void testBookServiceSearchingProtectedUser() {
		assertThrows(AccessDeniedException.class, () -> bookService.getBookDetails("natalie"));
	}
	
	@Test
	@WithMockUser(username = "natalie")
	public void testNameServiceSearchingUnprotectedUser() {
		Employee result = bookService.getBookDetails("emma");
		
		assertEquals("Emma Thompson", result.getName());
		
		assertEquals(1, result.getBooks().size());
		assertEquals("Karamazov Brothers", result.getBooks().get(0));
		
		assertEquals(2, result.getRoles().size());
		assertEquals("accountant", result.getRoles().get(0));
		assertEquals("reader", result.getRoles().get(1));
	}
}
