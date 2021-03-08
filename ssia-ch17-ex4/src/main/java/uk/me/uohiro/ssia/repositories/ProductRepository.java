package uk.me.uohiro.ssia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;

import uk.me.uohiro.ssia.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@PostFilter("filterObject.owner == authentication.name")
	List<Product> findProductByNameContains(String text);
	
}
