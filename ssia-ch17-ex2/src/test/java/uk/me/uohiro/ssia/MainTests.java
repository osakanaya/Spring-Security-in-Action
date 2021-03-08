package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import uk.me.uohiro.ssia.model.Product;
import uk.me.uohiro.ssia.services.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class MainTests {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testProductServiceWithNoUser() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));
        
        List<Product> unmodifiable = Collections.unmodifiableList(products);
        
        assertThrows(AuthenticationException.class, () -> productService.sellProducts(unmodifiable));
	}

	@Test
	@WithMockUser(username = "julien")
	public void testProductServiceWithUser() {

        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));

        List<Product> unmodifiable = Collections.unmodifiableList(products);

        assertThrows(UnsupportedOperationException.class, () -> productService.sellProducts(unmodifiable));
	}

	@Test
	@WithUserDetails("nikolai")
	public void testProductServiceWithImmutableProductList() throws Exception {
		try {
			mvc.perform(get("/sell"));
		} catch (Exception e) {
			assertTrue(e instanceof NestedServletException);
			
			Throwable cause = e.getCause();
			assertTrue(cause instanceof UnsupportedOperationException);
		}
	}	
	
}
