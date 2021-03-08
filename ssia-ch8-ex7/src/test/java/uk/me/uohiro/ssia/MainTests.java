package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	public void testCallingEmailWithValidEmail() throws Exception {
		mvc.perform(get("/email/test@example.com"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCallingEmailWithInvalidEmail() throws Exception {
		mvc.perform(get("/email/test@example.ro"))
			.andExpect(status().isUnauthorized());
	}
}
