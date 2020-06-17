package com.magroun.Ecommerce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value="select * FROM product ORDER BY RAND() LIMIT 4;", 	
			nativeQuery = true)
	public List<Product> getrandom();

}
