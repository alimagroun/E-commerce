package com.magroun.Ecommerce;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CartController {
	
	@Autowired
	private CartService cartservice; 
	
	@Autowired
	private UserService userservice; 
	@Autowired ProductService productservice;
	
	@GetMapping("cart")
	public String viewHomepageU(Model model, HttpSession session,HttpServletRequest request) {
	
		if (session.getAttribute("NOTES_SESSION") == null) {
			
	        @SuppressWarnings("unchecked")
			List<String> tc = (List<String>) request.getSession().getAttribute("TC");
		     if (tc == null) {
		            tc = new ArrayList<>();
		           request.getSession().setAttribute("TC", tc);
		        }

	        List<Cart> listCarts = new ArrayList();
	        for (int i=0; i<tc.size(); i=i+2) 
	        { 
	            String id  = tc.get(i);
	            String q = tc.get(i+1);
	            System.out.println("product is:"+id);
	            System.out.println("quantity:"+q);
	            
	            long idp = Long.parseLong(id);
	            int qua = Integer.parseInt(q);
	            
	            Product p = productservice.get(idp);
	         
	            
	            Cart ca = new Cart();	            
	            ca.setProduct(p);
	            ca.setQuantity(qua);
	            listCarts.add(ca);

	        }
			   @SuppressWarnings("unchecked")
			List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
				
		        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());
			
			model.addAttribute("listCarts", listCarts);
			
	        return "cart";
			
		}
		
		
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		 
		 long user_id = Long.parseLong(userdata.get(0));
		 User user = userservice.get(user_id);
		
		List<Cart> listCarts = cartservice.listCartbyuser(user);
	
		model.addAttribute("listCarts", listCarts);
	  model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());
	     
		return "cart";
	}	
	
	@RequestMapping("/deletec/{id}")
	public String deletec(@PathVariable(name = "id") int id,HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		if (session.getAttribute("NOTES_SESSION") == null) {
	        Product p = productservice.get(Long.valueOf(id));
	        String ProductName = p.getName();	
			 @SuppressWarnings("unchecked")
			List<String> tc = (List<String>) request.getSession().getAttribute("TC");
			 int x = tc.indexOf(Integer.toString(id));
			 tc.remove(x);
			 tc.remove(x);
			   request.getSession().setAttribute("TC", tc);
		redirectAttributes.addFlashAttribute("message", ProductName+" was removed from Shopping Cart.");   
			return "redirect:/cart";		
		}
		Cart c =cartservice.get(Long.valueOf(id));
		String ProductName = c.getProduct().getName();	
		cartservice.delete(id);
		redirectAttributes.addFlashAttribute("message", ProductName+" was removed from Shopping Cart.");  
		return "redirect:/cart";		
	}

	
	@GetMapping("check")
	public String checkoutpage(Model model, HttpSession session, HttpServletRequest request) {

		if (session.getAttribute("NOTES_SESSION") == null) {
			
			return "redirect:/login";
		}
		
		 @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		 
		 long user_id = Long.parseLong(userdata.get(0));
		 User user = userservice.get(user_id);
		
		List<Cart> listCarts = cartservice.listCartbyuser(user);
		
		float subtotal =0;
		for (Cart cart : listCarts) 
		{ 
			subtotal = subtotal+ (cart.getQuantity()*cart.getProduct().getPrice());
		}
		
		// String x = df.format(subtotal);
		
		String s = String.format ("%,.2f", subtotal);
		String t = String.format ("%,.2f", subtotal+10);
	
	
		request.getSession().setAttribute("total", subtotal+10);	
		
		
		model.addAttribute("subtotal", s);
		model.addAttribute("total", t);
		model.addAttribute("listCarts", listCarts);
	        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());
	        model.addAttribute("sizeCart", cartservice.sizeCart(session));
		return "checkout";
	}	

	

	
	
	
	
}
