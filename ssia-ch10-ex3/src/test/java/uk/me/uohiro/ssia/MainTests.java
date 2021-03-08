package uk.me.uohiro.ssia;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import uk.me.uohiro.ssia.repositories.JpaTokenRepository;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(
	exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	}
)
class MainTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private JpaTokenRepository tokenRepository;
	
	@Test
	public void testHelloGET() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testHelloPOST() throws Exception {
		mvc.perform(post("/hello"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void testHelloPOSTWithCSRF() throws Exception {
		mvc.perform(post("/hello").with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCiaoPOST() throws Exception {
		mvc.perform(post("/ciao"))
			.andExpect(status().isOk());
	}
	
}
