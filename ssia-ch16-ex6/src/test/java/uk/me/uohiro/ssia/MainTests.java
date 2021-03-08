package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.services.NameService;

@SpringBootTest
public class MainTests {

	@Autowired
	private NameService nameService;

	@Test
	public void testNameServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> nameService.getName1());
		assertThrows(AuthenticationException.class, () -> nameService.getName2());
	}

	@Test
	@WithMockUser(username = "mary", roles = "MANAGER")
	public void testNameServiceWithUserHavingWrongRole() {
		assertThrows(AccessDeniedException.class, () -> nameService.getName1());
		assertThrows(AccessDeniedException.class, () -> nameService.getName2());
	}

	@Test
	@WithMockUser(username = "mary", roles = "ADMIN")
	void testNameServiceWithUserHavingCorrectRole() {
		String result1 = nameService.getName1();
		String result2 = nameService.getName2();

		assertEquals("Fantastico1", result1);
		assertEquals("Fantastico2", result2);
	}
}
