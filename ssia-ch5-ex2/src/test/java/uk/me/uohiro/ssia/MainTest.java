package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
	@WithMockUser("mary")
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(content().string("Hello, mary!"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("mary")
	public void ciaoAuthenticated() throws Exception {
		mvc.perform(get("/ciao"))
			.andExpect(content().string("Ciao, mary!"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser("mary")
	public void holaAuthenticated() throws Exception {
		mvc.perform(get("/hola"))
			.andExpect(content().string("Hola, mary!"))
			.andExpect(status().isOk());
	}

}
