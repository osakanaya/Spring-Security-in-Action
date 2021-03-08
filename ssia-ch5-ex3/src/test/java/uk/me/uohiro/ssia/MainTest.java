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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MainTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void helloUnauthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(header().string("message", "Luke, I am your father!"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(content().string("Hello!"))
			.andExpect(status().isOk());
	}
	
}
