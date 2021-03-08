package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class MainTests {

	@Autowired
	private WebTestClient client;
	
	@Test
	public void testCallHelloWithoutUser() {
		client.get().uri("/hello").exchange().expectStatus().isUnauthorized();
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallHelloWithValidUser() {
		client.get().uri("/hello").exchange().expectStatus().isOk();
	}
	
	@Test
	@WithMockUser
	public void testCallHelloWithMockUser() {
		client.get().uri("/hello").exchange().expectStatus().isOk();
	}
	
	@Test
	public void testCallHelloWithValidUserWithMoockUser() {
		client.mutateWith(mockUser()).get().uri("/hello").exchange().expectStatus().isOk();
	}
}
