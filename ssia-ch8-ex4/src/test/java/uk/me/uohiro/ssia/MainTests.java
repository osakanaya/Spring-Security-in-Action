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
	public void testCallingProductWithValidParam() throws Exception {
		mvc.perform(get("/product/12345"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCallingProductWithInvalidParam() throws Exception {
		mvc.perform(get("/product/abc45"))
			.andExpect(status().isUnauthorized());
	}
	
}
