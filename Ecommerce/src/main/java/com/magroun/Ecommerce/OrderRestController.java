package com.magroun.Ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

	@Autowired
 	OrderService orderservice;
	
	
	@PostMapping("/order/save")
	public ResponseEntity<Void> updateCart(@RequestBody Order order) {

		
	Order o = orderservice.get(order.getOrder_id());
	o.setStatus(order.getStatus());
	orderservice.save(o);
	    	
	return new ResponseEntity<Void>(HttpStatus.OK);
}
	
	
	
}
