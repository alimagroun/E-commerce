package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ApplicationController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private UserService service1;
	
	@Autowired
 private EmailService emailService;	
	
	@Autowired
 private CartService cartservice;	
	
	@RequestMapping(value = "/saveuser", method = RequestMethod.POST)
	public String saveUsert(@ModelAttribute("user") User user, BindingResult bindingResult,  HttpServletRequest request, RedirectAttributes redirectAttributes) {
        @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
        if (userdata == null) {
            userdata = new ArrayList<>();
            request.getSession().setAttribute("NOTES_SESSION", userdata);
        }
        if (!userdata.isEmpty()) {
        	if (!user.getEmail().equalsIgnoreCase(userdata.get(1))) {
    		if (service1.test(user.getEmail()))
   		 {
    			System.out.println(user.getEmail());
    			System.out.println(userdata.get(1));
    			request.getSession().setAttribute("NOTES_SESSION", userdata);
    			redirectAttributes.addFlashAttribute("email", "This email is already in use.");
   			return "redirect:/myaccount";	
   		 }		  	        	
        }
        	if(!userdata.isEmpty()) {
        		service1.save(user);
    	        userdata.add(1,user.getEmail());
    	        userdata.add(2,user.getFirst_name());
    	        userdata.add(3,user.getLast_name());
    	        userdata.add(4,user.getAddress1());
    	        userdata.add(5,user.getCity());
    	        userdata.add(6,user.getState());
    	        userdata.add(7,user.getPostalcode());
    	        userdata.add(8,user.getCountry());
    	        userdata.add(9,user.getUser_type());
    	        request.getSession().setAttribute("NOTES_SESSION", userdata);
    	        redirectAttributes.addFlashAttribute("message", "Your personal details have been changed!");
    	        return "redirect:/myaccount";
        	}
        }
		if (userdata.isEmpty()&service1.test(user.getEmail()))
		 {
			bindingResult.rejectValue("email", "", "This email is already in use, Log in");
			return "login-register";	
		 
		 }	
		user.setUser_type("customer");
		service1.save(user);	
		Long Sid = user.getUser_id();
	        userdata.add(Sid.toString());
	        userdata.add(user.getEmail());
	        userdata.add(user.getFirst_name());
	        userdata.add(user.getLast_name());
	        userdata.add(user.getAddress1());
	        userdata.add(user.getCity());
	        userdata.add(user.getState());
	        userdata.add(user.getPostalcode());
	        userdata.add(user.getCountry());
	        request.getSession().setAttribute("NOTES_SESSION", userdata);
	        
	        @SuppressWarnings("unchecked")
			List<String> tc = (List<String>) request.getSession().getAttribute("TC");
		     if (tc == null) {
		            tc = new ArrayList<>();
		           request.getSession().setAttribute("TC", tc);
		        }
	    Boolean    b =(tc.isEmpty());
	    if (b == false) {
	   
	        for (int i=0; i<tc.size(); i=i+2) 
	        { 
	        
	            
	            long id = Long.parseLong( tc.get(i));
	            int qua = Integer.parseInt(tc.get(i+1));
	            
	            Product pr = service.get(id);
	            
	           Cart cart = new Cart();
	            	cart.setUser(user);
	            	cart.setProduct(pr);
	            	cart.setQuantity(qua);
	            	cartservice.save(cart);
	       
	        }	
		
		return "redirect:/cart";
		
		}
	    return "redirect:/home";
	}
	
	
	
	@RequestMapping("password-reset")
	public String showforgetPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
	return "forget-password";
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendpassword(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes ) {
		
		if (!service1.test(user.getEmail()))
		 {
		bindingResult.rejectValue("email", "", "Invalid email");
		
			System.out.println("email not found");
			return "forget-password";
			
		
		}
		String password =service1.Findpassword(user.getEmail());
		emailService.sendEmail(user.getEmail(),password);
		
		System.out.println(user.getEmail());
		redirectAttributes.addFlashAttribute("message", "Please check your email for a message with your password.");
					return "redirect:/login";
		
	
	}
	
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String sendf(@RequestParam("product_id") String product_id, @RequestParam("quantity") String quantity,HttpSession session, HttpServletRequest request
			, RedirectAttributes redirectAttributes) {
        Product p = service.get(Long.parseLong(product_id));
        String ProductName = p.getName();	
		if (session.getAttribute("NOTES_SESSION") == null) {
	        List<String> tc = (List<String>) request.getSession().getAttribute("TC");
	        //check if notes is present in session or not
	     if (tc == null) {
	            tc = new ArrayList<>();
	            // if notes object is not present in session, set notes in the request session
	           request.getSession().setAttribute("TC", tc);
	        }
 
	    if (tc.indexOf(product_id)!= -1) {
	    	int x = tc.indexOf(product_id);
	   
	    	int i = Integer.parseInt(tc.get(x+1));
	    	i=i+Integer.parseInt(quantity);
	    	tc.set(x+1, String.valueOf(i));
	    	request.getSession().setAttribute("TC", tc);
	    	redirectAttributes.addFlashAttribute("message", ProductName+" - Quantity has been updated to: "+i);
	    	return "redirect:/cart";
	    	
	    }
	    
	     
	     tc.add(product_id);
		tc.add(quantity);	
		
			
		
		request.getSession().setAttribute("TC", tc);
	
		// this code is for testing
	        List<String> after = (List<String>) request.getSession().getAttribute("TC");

	        for (String j : after) 
	        { 
	        System.out.println(j);
	        }

	        redirectAttributes.addFlashAttribute("message", ProductName+" has been added by to your cart.");
			return "redirect:/cart";
        }
		
        List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
		
		User user0 = new User();
		Product product0 = new Product();
		user0.setUser_id(Long.parseLong(userdata.get(0)));
		product0.setId(Long.parseLong(product_id));
		
		if(cartservice.exists(user0, product0))
				{
			Cart cart0 = cartservice.cartbyuserandproduct(user0, product0);
			int i = cart0.getQuantity();
			i= i+Integer.parseInt(quantity);
			cart0.setQuantity(i);
			cartservice.save(cart0);
	redirectAttributes.addFlashAttribute("message", ProductName+" - Quantity has been updated to: "+i);
					return "redirect:/cart";
				}
				
				
				User user = service1.get(Long.parseLong(userdata.get(0)));
				Product product = service.get(Long.parseLong(product_id));			
				
				
				Cart cart =new Cart();
				cart.setProduct(product);
				cart.setUser(user);
				cart.setQuantity(Integer.parseInt(quantity));
             cartservice.save(cart);
             System.out.println("new item has been added");
             redirectAttributes.addFlashAttribute("message", ProductName+" has been added by to your cart.");
					return "redirect:/cart";
	}

	@GetMapping("home")
	public String viewHomepageU(Model model, HttpSession session) {
		List<Product> listProducts = service.listrandom();
		 
		model.addAttribute("listProducts", listProducts);
	
		   @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
	        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());	
			if (session.getAttribute("NOTES_SESSION") == null) {							
				model.addAttribute("sizeCart",cartservice.sizeCart2(session));
			}
			else
	        {model.addAttribute("sizeCart", cartservice.sizeCart(session));}
	     
		return "indexU";
	}
	
	
	@RequestMapping("login")
	public String showLoginPage(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
        if (userdata == null) {
        	userdata = new ArrayList<>();    
    		User user = new User();
    		model.addAttribute("user", user);
    		return "login-register";
        }
        
        return "error";

	}
	
	
	
	
	
	@RequestMapping(value = "/checkuser", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request) {
		
		if(service1.verify_login(user.getEmail(), user.getPassword())) {

		
		user = service1.Finduserbyemail(user.getEmail());
		Long Sid = user.getUser_id();
		String Semail = user.getEmail();
		String Sfirstname = user.getFirst_name();
		String ln = user.getLast_name();
		String a = user.getAddress1();
		String c = user.getCity();
		String s = user.getState();
		String p = user.getPostalcode();
		String co = user.getCountry();
		
		
		
	        @SuppressWarnings("unchecked")
			List<String> userdata = (List<String>) request.getSession().getAttribute("NOTES_SESSION");
	        //check if notes is present in session or not
	        if (userdata == null) {
	            userdata = new ArrayList<>();
	            // if notes object is not present in session, set notes in the request session
	            request.getSession().setAttribute("NOTES_SESSION", userdata);
	        }
	    
	        userdata.add(Sid.toString());
	        userdata.add(Semail);
	        userdata.add(Sfirstname);
	        userdata.add(ln);
	        userdata.add(a);
	        userdata.add(c);
	        userdata.add(s);
	        userdata.add(p);
	        userdata.add(co);
	        userdata.add(user.getUser_type());
	        request.getSession().setAttribute("NOTES_SESSION", userdata);
	        
			if (user.getUser_type().equals("admin"))
			{ return "redirect:/";}
	        
	        @SuppressWarnings("unchecked")
			List<String> tc = (List<String>) request.getSession().getAttribute("TC");
		     if (tc == null) {
		            tc = new ArrayList<>();
		           request.getSession().setAttribute("TC", tc);
		        }
	    Boolean    b =(tc.isEmpty());
	    if (b == false) {
	   
	        for (int i=0; i<tc.size(); i=i+2) 
	        { 
	        
	            
	            long id = Long.parseLong( tc.get(i));
	            int qua = Integer.parseInt(tc.get(i+1));
	            
	            Product pr = service.get(id);
	            
	            Cart cart = cartservice.cartbyuserandproduct(user, pr);
	            if (cart == null) {
	            	cart = new Cart();
	            	cart.setUser(user);
	            	cart.setProduct(pr);
	            	cart.setQuantity(qua);
	            	cartservice.save(cart);
	            }
	            else 
	            {
	            	cart.setQuantity(qua);
	            	cartservice.save(cart);
	            }
	            
	        }
	    	
	    }
	        
	        return "redirect:/home";
		}
		
		
		else 
		 {
			bindingResult.rejectValue("email", "", "Incorrect email or password");
			return "login-register";
		}
		
	}
    @PostMapping("/invalidate/session")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
      
        return "redirect:/home";
    }
		
	
}
