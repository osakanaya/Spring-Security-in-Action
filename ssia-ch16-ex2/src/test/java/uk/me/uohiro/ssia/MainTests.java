package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.config.services.NameService;

@SpringBootTest
public class MainTests {

	@Autowired
	private NameService nameService;
	
	@Test
	public void testNameServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> nameService.getSecretNames("john"));
	}
	
	@Test
	@WithMockUser(username = "bill")
	public void testNameServiceCallingTheSecretNamesMethodWithDifferentUser() {
		assertThrows(AccessDeniedException.class, () -> nameService.getSecretNames("emma"));
	}
	
	@Test
	@WithMockUser(username = "emma")
	public void testNameServiceCallingTheSecretNamesMethodWithAuthenticatedUser() {
		List<String> result = nameService.getSecretNames("emma");

		assertEquals(1, result.size());
		assertEquals("Fantastico", result.get(0));
	}
}
