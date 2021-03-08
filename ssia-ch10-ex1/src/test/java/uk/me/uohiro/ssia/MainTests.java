package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testHelloGET() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testHelloPOST() throws Exception {
		mvc.perform(post("/hello"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void testHelloPOSTWithCSRF() throws Exception {
		mvc.perform(post("/hello").with(csrf()))
			.andExpect(status().isOk());
	}
	
}
