package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Order_itemRepository extends JpaRepository<Order_item, Long> {

	
	public List<Order_item> findByOrder(Order order);
}
