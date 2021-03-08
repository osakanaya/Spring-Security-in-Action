package uk.me.uohiro.ssia;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class MainTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testAccessTokenIsObtainedUsingValidUserAndClient() throws Exception {
		mvc.perform(
			post("/oauth/token")
				.with(httpBasic("client", "secret"))
				.queryParam("grant_type", "password")
				.queryParam("username", "john")
				.queryParam("password", "12345")
				.queryParam("scope", "read")
			)
			.andExpect(jsonPath("$.access_token").exists())
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCheckTokenEndpoint() throws Exception {
		String content = 
			mvc.perform(post("/oauth/token")
					.with(httpBasic("client", "secret"))
					.queryParam("grant_type", "password")
					.queryParam("username", "john")
					.queryParam("password", "12345")
					.queryParam("scope", "read")
			)
			.andReturn()
				.getResponse().getContentAsString();
		
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, String> map = mapper.readValue(content, Map.class);
		
		mvc.perform(get("/oauth/check_token")
				.with(httpBasic("resourceserver", "resourceserversecret"))
				.queryParam("token", map.get("access_token"))
		)
		.andDo(print())
		.andExpect(jsonPath("$.user_name").value("john"))
		.andExpect(jsonPath("$.client_id").value("client"))
		.andExpect(status().isOk());
	}
	
}
