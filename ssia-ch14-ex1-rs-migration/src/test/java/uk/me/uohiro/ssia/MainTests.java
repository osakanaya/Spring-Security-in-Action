package uk.me.uohiro.ssia;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class MainTests {

	@Autowired
	private MockMvc mvc;
	
	private static WireMockServer wireMockServer;
	
	@BeforeAll
	static void init() {
		wireMockServer = new WireMockServer(new WireMockConfiguration().port(8080));
		wireMockServer.start();
		
		WireMock.configureFor("localhost", 8080);
	}
	
	@AfterAll
	static void tearDown() {
		wireMockServer.stop();
	}
	
	@Test
	public void testAccessTokenIsObtainedUsingClientCredentials() throws Exception {
		String token = UUID.randomUUID().toString();
		
		stubFor(WireMock.post(urlMatching("/oauth/check_token"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withStatus(OK.value())
						.withBody("{\"active\":true,\"exp\":1587946446,\"user_name\":\"john\",\"authorities\":[\"read\"],\"client_id\":\"client\",\"scope\":[\"read\"]}")));
		
		mvc.perform(MockMvcRequestBuilders.get("/hello")
			.header("Authorization", "Bearer " + token))
			.andExpect(content().string("Hello!"))
			.andExpect(status().isOk());
	}
}
