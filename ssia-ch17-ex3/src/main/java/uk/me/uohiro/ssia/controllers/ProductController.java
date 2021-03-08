package uk.me.uohiro.ssia.controllers;

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

	@GetMapping("/find")
	public List<Product> sellProuct() {
		return productService.findProducts();
	}
}
