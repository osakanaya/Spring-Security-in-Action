package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
			post("/oauth/token")
				.with(httpBasic("client", "secret"))
				.queryParam("grant_type", "client_credentials")
				.queryParam("scope", "info")
			)
			.andExpect(jsonPath("$.access_token").exists())
			.andExpect(status().isOk());
	}
	
}
