package uk.me.uohiro.ssia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.entities.Product;
import uk.me.uohiro.ssia.repositories.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products/{text}")
	public List<Product> findProductsContaining(@PathVariable String text) {
		return productRepository.findProductByNameContains(text);
	}
}
