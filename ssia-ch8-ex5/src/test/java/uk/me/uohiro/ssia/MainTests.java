package uk.me.uohiro.ssia;

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
class MainTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithUserDetails("john")
	public void testCallingHelloWithAuthentication() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallingHelloVariantWithAuthentication() throws Exception {
		mvc.perform(get("/hello/"))
			.andExpect(status().isOk());
	}

	@Test
	public void testCallingHelloWithoutAuthentication() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testCallingHelloVariantWithoutAuthentication() throws Exception {
		mvc.perform(get("/hello/"))
			.andExpect(status().isOk());
	}

}
