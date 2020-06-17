package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	
	Cart findByUserAndProduct(User user,Product product);
	
	public List<Cart> findByUser(User user);
	
	

	

}
