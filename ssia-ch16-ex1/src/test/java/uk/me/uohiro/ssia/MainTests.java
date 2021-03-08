package uk.me.uohiro.ssia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.config.services.NameService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MainTests {

	@Autowired
	private NameService nameService;
	
	@Test
	public void testNameServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> nameService.getName());
	}
	
	@Test
	@WithMockUser(authorities = "read")
	public void testNameServiceWithUserButWrongAuthority() {
		assertThrows(AccessDeniedException.class, () -> nameService.getName());
	}
	
	@Test
	@WithMockUser(authorities = "write")
	public void testNameServiceWithUserButCorrectAuthority() {
		String result = nameService.getName();

		assertEquals("Fantastico", result);
	}
}
