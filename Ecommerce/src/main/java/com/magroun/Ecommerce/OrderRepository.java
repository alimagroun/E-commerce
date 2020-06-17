package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	
	
	public List<Order> findByUser(User user);

}
