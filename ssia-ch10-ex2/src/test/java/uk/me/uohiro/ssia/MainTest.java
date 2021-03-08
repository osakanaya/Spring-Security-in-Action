package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
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
	public void loggingInWithWrongUser() throws Exception {
		mvc.perform(formLogin())
			.andExpect(unauthenticated());
	}
	
	@Test
	public void loggingInWithValidUser() throws Exception {
		mvc.perform(formLogin().user("mary").password("12345"))
			.andExpect(redirectedUrl("/main"))
			.andExpect(status().isFound())
			.andExpect(authenticated());
	}
}
