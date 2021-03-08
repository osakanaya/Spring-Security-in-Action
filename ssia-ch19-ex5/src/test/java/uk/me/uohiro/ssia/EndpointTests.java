package uk.me.uohiro.ssia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class EndpointTests {

	@Autowired
	private WebTestClient client;
	
	@Test
	public void testCallWithoutUser() {
		client.get().uri("/hello").exchange().expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	public void testCallHelloWithUserButNotAValidRole() {
		client.get().uri("/hello").exchange().expectStatus().isForbidden();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCallHelloWithUserAndValidRole() {
		client.get().uri("/hello").exchange().expectStatus().isOk();
	}
	
}
