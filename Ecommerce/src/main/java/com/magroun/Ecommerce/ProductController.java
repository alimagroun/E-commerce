package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ProductController {

@Autowired 
ProductService productservice;	
@Autowired
CartService cartservice;
	
	@RequestMapping("shop")
	public String viewShopPage(Model model,HttpSession session) {
		List<Product> listProducts = productservice.listAll();
		model.addAttribute("listProducts", listProducts);
		   @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
	        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());	
			if (session.getAttribute("NOTES_SESSION") == null) {							
				model.addAttribute("sizeCart",cartservice.sizeCart2(session));
			}
			else
	        {model.addAttribute("sizeCart", cartservice.sizeCart(session));}
		return "shop";
	}	
	
	
}
