package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.model.Product;
import uk.me.uohiro.ssia.services.ProductService;

@SpringBootTest
public class MainTests {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testProductServiceWithNoUser() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));
        
        assertThrows(AuthenticationException.class, () -> productService.sellProducts(products));
	}

	@Test
	@WithMockUser(username = "julien")
	public void testProductServiceWithUser() {

        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));

        List<Product> result = productService.sellProducts(products);
        
        List<Product> expected = new ArrayList<Product>();
        expected.add(new Product("chocolate", "julien"));
        
        assertEquals(expected, result);
	}
}
