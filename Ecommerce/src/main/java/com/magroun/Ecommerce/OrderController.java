package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	@Autowired
	OrderService orderservice;
	@Autowired
	CartService cartservice;
	@Autowired
	Order_itemService order_itemservice;
	
	@Autowired
	UserService userservice;
	
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String sendf(@RequestParam("user_id") String user_id, HttpSession session) {
	
	Float	t= (Float )session.getAttribute("total");	
		User user = new User();
        Order order = new Order();	
		user.setUser_id(Long.parseLong(user_id));
		
		order.setUser(user);
	   order.setTotal_amount(t);
	   order.setStatus("Processing");
	   orderservice.save(order);

	   System.out.print(order.getOrder_id());
	
	   List<Cart> listCarts = cartservice.listCartbyuser(user);
			
		for (Cart cart : listCarts) 
		{ 
			Order_item oi = new Order_item();
			oi.setOrder(order);
			oi.setProduct(cart.getProduct());
			oi.setPrice(cart.getProduct().getPrice());
			oi.setQuantity(cart.getQuantity());
			
			order_itemservice.save(oi);
			
			cartservice.delete(cart.getId());
		}

		
		return "redirect:/orders";
}
	
	@GetMapping("orders")
	public String viewHomepageU(Model model, HttpSession session) {
		 @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
	        if (userdata == null) {
	            userdata = new ArrayList<>();
		 return "redirect:/login";}
		 long user_id = Long.parseLong(userdata.get(0));
		 User user = userservice.get(user_id);
		 
			List<Order> listOrders = orderservice.listOrderbyuser(user);
		
			List<Order_item> items = new ArrayList<>();
			for (Order o : listOrders) 
			{ 
			items.addAll(order_itemservice.listOrderbyorder(o)); 
			}
				
			model.addAttribute("listOrders", listOrders);
			model.addAttribute("items", items);
			model.addAttribute("sizeCart", cartservice.sizeCart(session));
			model.addAttribute("notesSession", userdata);
		 return "orders";
	}
	
	@GetMapping("orders_admin")
	public String viewOrders_AdminPage(Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		if (!userdata.get(9).equals("admin")) 
		{return "error";}
		
		 
			List<Order> listOrders = orderservice.listAll();
		
			List<Order_item> items = new ArrayList<>();
			for (Order o : listOrders) 
			{ 
			items.addAll(order_itemservice.listOrderbyorder(o)); 
			}
				
			model.addAttribute("listOrders", listOrders);
			model.addAttribute("items", items);
		 
		 return "orders_admin";
	}
}