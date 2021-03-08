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
	public void testCallingVideoWithoutAuthentication() throws Exception {
		mvc.perform(get("/video/ca/fr"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallingVideoWithAuthenticationWithoutPremium() throws Exception {
		mvc.perform(get("/video/ca/fr"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithUserDetails("john")
	public void testCallingVideoWithAuthenticationWithoutPremiumForRO() throws Exception {
		mvc.perform(get("/video/ro/ro"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithUserDetails("jane")
	public void testCallingVideoWithAuthenticationWithPremiumForRO() throws Exception {
		mvc.perform(get("/video/ro/ro"))
			.andExpect(status().isOk());
	}
	
}
