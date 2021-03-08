package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.entities.Product;
import uk.me.uohiro.ssia.repositories.ProductRepository;

@SpringBootTest
@EnableAutoConfiguration(
	exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	}
)
public class MainTests {

	@MockBean
	private ProductRepository productRepository;
	
	@Test
	public void testProductServiceWithNoUser() {
		List<Product> result = productRepository.findProductByNameContains("c");
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	@WithMockUser(username = "julien")
	public void testProductServiceWithUser() {
		List<Product> result = productRepository.findProductByNameContains("c");
		
		result.forEach(p -> {
			assertEquals("julien", p.getOwner());
			assertTrue(p.getName().contains("c"));
		});
	}
		
}
