package uk.me.uohiro.ssia.services;

import java.util.List;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.model.Product;

@Service
public class ProductService {

	@PreFilter("filterObject.owner == authentication.name")
	public List<Product> sellProducts(List<Product> products) {
		return products;
	}
}
