package uk.me.uohiro.ssia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.me.uohiro.ssia.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
