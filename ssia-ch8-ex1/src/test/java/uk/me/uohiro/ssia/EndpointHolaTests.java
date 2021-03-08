package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointHolaTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testFailedAuthentication() throws Exception {
		mvc.perform(get("/hola"))
			.andExpect(unauthenticated());
	}
	
	@Test
	@WithUserDetails("jane")
	public void testSuccessfulAuthorization1() throws Exception {
		mvc.perform(get("/hola"))
			.andExpect(authenticated())
			.andExpect(status().isOk());
	}	

	@Test
	@WithUserDetails("john")
	public void testSuccessfulAuthorization2() throws Exception {
		mvc.perform(get("/hola"))
			.andExpect(authenticated())
			.andExpect(status().isOk());
	}	
}
