package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

	@Autowired
	private MockMvc mvc;

	@Value("${authorization.key}")
	private String key;
	
	@Test
	public void testHelloNoAuthorizationHeader() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void testHelloUsingInvalidAuthorizationHeader() throws Exception {
		mvc.perform(get("/hello").header("Authorization", "12345"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void testHelloUsingValidAuthorizationHeader() throws Exception {
		mvc.perform(get("/hello").header("Authorization", key))
			.andExpect(status().isOk());
	}
}
