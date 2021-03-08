package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
	public void helloUnauthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello").with(user("john")))
			.andExpect(content().string("Hello!"))
			.andExpect(status().isOk());
	}

	@Test
	public void helloAuthenticatedWithWrongUser() throws Exception {
		mvc.perform(get("/hello").with(httpBasic("bill", "12345")))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void helloAuthenticatedWithValidUser() throws Exception {
		mvc.perform(get("/hello").with(httpBasic("john", "12345")))
			.andExpect(content().string("Hello!"))
			.andExpect(status().isOk());
	}	
}
