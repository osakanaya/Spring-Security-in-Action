package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import uk.me.uohiro.ssia.config.WithCustomUser;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void helloAuthenticatingWithValidUser() throws Exception {
		mvc.perform(get("/hello").with(httpBasic("john", "12345")))
			.andExpect(status().isOk())
			.andExpect(content().string("Hello!"));
	}

	@Test
	public void helloAuthenticatingWithInvalidUser() throws Exception {
		mvc.perform(get("/hello").with(httpBasic("mary", "12345")))
			.andExpect(status().isUnauthorized());
	}
}
