package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testCallingABCUnauthenticated() throws Exception {
		mvc.perform(get("/a/b/c"))
			.andExpect(unauthenticated());
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallingABCAuthenticated() throws Exception {
		mvc.perform(get("/a/b/c"))
			.andExpect(status().isOk());
	}

	@Test
	public void testCallingABUnauthenticated() throws Exception {
		mvc.perform(get("/a/b"))
			.andExpect(unauthenticated());
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallingABAuthenticated() throws Exception {
		mvc.perform(get("/a/b"))
			.andExpect(status().isOk());
	}

	@Test
	public void testCallingAnyOhterEndpoints() throws Exception {
		mvc.perform(get("/a"))
			.andExpect(status().isOk());

		mvc.perform(post("/a"))
			.andExpect(status().isOk());
	}
}
