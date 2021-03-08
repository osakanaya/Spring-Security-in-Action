package uk.me.uohiro.ssia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import reactor.test.StepVerifier;
import uk.me.uohiro.ssia.controllers.HelloController;

@SpringBootTest
public class MethodTests {

	@Autowired
	private HelloController helloController;
	
	@Test
	public void testCallHelloWithoutUser() {
		StepVerifier.create(helloController.hello())
			.expectError(AccessDeniedException.class)
			.verify();
	}
	
	@Test
	@WithMockUser
	public void testCallHelloWithUserButWrongRole() {
		StepVerifier.create(helloController.hello())
			.expectError(AccessDeniedException.class)
			.verify();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCallHelloWithUserAndAdminRole() {
		StepVerifier.create(helloController.hello())
			.expectNext("Hello!")
			.verifyComplete();
	}
	
}
