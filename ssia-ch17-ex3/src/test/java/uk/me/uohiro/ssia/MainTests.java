package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.model.Product;
import uk.me.uohiro.ssia.services.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class MainTests {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testProductServiceWithNoUser() {
		assertThrows(AuthenticationException.class, () -> productService.findProducts());
	}

	@Test
	@WithMockUser(username = "julien")
	public void testProductServiceWithUser() {
		List<Product> result = productService.findProducts();
		
		result.forEach(p -> assertEquals("julien", p.getOwner()));
	}
		
}
