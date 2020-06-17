package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CartService {
	
	
	
	@Autowired
	private CartRepository repo;
	
	public void save(Cart cart) {
		repo.save(cart);
	}
	

	public boolean exists(User user, Product product) {
		Cart cart; 
		cart = repo.findByUserAndProduct(user, product);
		if (cart == null)return false;
		return true;
	}
	
	public Cart cartbyuserandproduct(User user, Product product) {
		
		return repo.findByUserAndProduct(user, product);
		
	}
	
	
	public List<Cart> listCartbyuser(User user){
		return repo.findByUser(user);
		
	}
	
	
	public Cart get(long id) {
		return repo.findById(id).get();
	}
	public void delete(long id) {
		repo.deleteById(id);
	}
	
	public int sizeCart (HttpSession session){
        @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		User user = new User();
		user.setUser_id(Long.parseLong(userdata.get(0)));
		
		List<Cart> c = listCartbyuser(user);		
		int x =0;
		for (Cart j : c) 
		{ 
		   int q = j.getQuantity();
		   x=x+q;
		}
		return x;
				
	}
	
	public int sizeCart2 (HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<String> tc =(List<String>) session.getAttribute("TC");
        if (tc == null) {
            tc = new ArrayList<>(); return 0;}
        int x =0;
        for (int i=0; i<tc.size(); i=i+2) 
        {       
            int q = Integer.parseInt(tc.get(i+1));
          x=x+q;  
        }
		
		
		return x;
	}
	
	
	
	
}
