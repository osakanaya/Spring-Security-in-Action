package uk.me.uohiro.ssia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	@WithUserDetails("john")
	public void helloAuthenticated() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(content().string("Hello!"))
			.andExpect(status().isOk());
	}	
}
