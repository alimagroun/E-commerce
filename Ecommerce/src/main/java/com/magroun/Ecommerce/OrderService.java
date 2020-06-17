package com.magroun.Ecommerce;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository repo;
	

	
	public void save(Order order) {
		repo.save(order);
	}
	
	
	public List<Order> listOrderbyuser(User user){
		return repo.findByUser(user);
		
	}
	
	public List<Order> listAll() {
		return repo.findAll();
	}
	
	public Order get(long id) {
		return repo.findById(id).get();
	}
	
	
}
