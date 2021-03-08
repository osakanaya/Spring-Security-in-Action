package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testAccessTokenIsObtainedUsingValidUserAndClient() throws Exception {
		mvc.perform(
			get("/oauth/authorize")
				.queryParam("response_type", "code")
				.queryParam("client_id", "client1")
				.queryParam("scope", "read")
			)
			.andExpect(redirectedUrl("http://localhost/login"))
			.andExpect(status().isFound());
	}
	
}
