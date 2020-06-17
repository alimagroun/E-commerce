package com.magroun.Ecommerce;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Order_itemService {
	
	@Autowired
	private Order_itemRepository repo;

	public void save(Order_item order_item) {
		repo.save(order_item);
	}
	
	public List<Order_item> listOrderbyorder(Order order){
		return repo.findByOrder(order);
	}
	 
}
