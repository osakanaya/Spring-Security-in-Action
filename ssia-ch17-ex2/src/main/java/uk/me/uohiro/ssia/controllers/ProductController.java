package uk.me.uohiro.ssia.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.me.uohiro.ssia.model.Product;
import uk.me.uohiro.ssia.services.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/sell")
	public List<Product> sellProuct() {
		List<Product> products = new ArrayList<Product>();
		
		products.add(new Product("beer", "nikolai"));
		products.add(new Product("candy", "nikolai"));
		products.add(new Product("chocolate", "julien"));
		
		List<Product> unmodifiable = Collections.unmodifiableList(products);
		
		return productService.sellProducts(unmodifiable);
	}
}
