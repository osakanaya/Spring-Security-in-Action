package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
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
	public void testCallHelloWithUser() {
		client.mutateWith(mockJwt()).get().uri("/hello").exchange().expectStatus().isOk();
	}
	
}
