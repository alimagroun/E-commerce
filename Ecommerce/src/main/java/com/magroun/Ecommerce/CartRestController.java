package com.magroun.Ecommerce;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CartRestController {

	@Autowired
	CartService cartservice;

	
	
	@PostMapping("/cart/save")
	public ResponseEntity<Void> updateCart(@RequestBody Cart cart,HttpSession session, HttpServletRequest request) {
 
int q = cart.getQuantity();

 
if (session.getAttribute("NOTES_SESSION") == null) {
	String product_id = String.valueOf(cart.getId());

	   @SuppressWarnings("unchecked")
	List<String> tc = (List<String>) request.getSession().getAttribute("TC");
	   
	   
	    	int x = tc.indexOf(product_id);
	   
	    	
	    
	    	tc.set(x+1, String.valueOf(q));
	    	request.getSession().setAttribute("TC", tc);
	    	
	return new ResponseEntity<Void>(HttpStatus.OK);
}

		
		Cart c = cartservice.get(cart.getId());
		
	    c.setQuantity(q);
	   
	   	cartservice.save(c);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	

	
	
	
	
	
	
	
	
	
}
