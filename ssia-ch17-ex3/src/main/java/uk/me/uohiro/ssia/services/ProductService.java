package uk.me.uohiro.ssia.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.model.Product;

@Service
public class ProductService {

	@PostFilter("filterObject.owner == authentication.name")
	public List<Product> findProducts() {
		List<Product> products = new ArrayList<Product>();
		
		products.add(new Product("beer", "nikolai"));
		products.add(new Product("candy", "nikolai"));
		products.add(new Product("chocolate", "julien"));
		
		return products;
	}
}
