package com.magroun.Ecommerce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AppController {
	private static String UPLOADED_FOLDER = "D:\\Ecommerce\\src\\main\\resources\\static\\images\\product\\";
	
	@Autowired
	private ProductService service; 
	@Autowired
	private CartService cartservice;
	
			
	
	@RequestMapping("/")
	public String viewHomePage(Model model, HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		if (!userdata.get(9).equals("admin")) 
		{return "error";}
		model.addAttribute("notesSession", userdata);
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewProductPage(Model model,HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		if (!userdata.get(9).equals("admin")) 
		{return "error";}
		Product product = new Product();
		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		
		
   if (file.isEmpty()) {
	      //      redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            service.save(product);
	            return "redirect:/";
	        }

	        try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	            redirectAttributes.addFlashAttribute("message",
	                    "You successfully added a new product.");
	            
	            
	        	product.setPicture(file.getOriginalFilename());
	        	service.save(product);
	            
	            
	            

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product", product);
		
		return  mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
		Product p= service.get(Long.valueOf(id));
		String name = p.getName();
		try {
			service.delete(id);
		} catch (Exception e) {
			
			
            redirectAttributes.addFlashAttribute("message","You can't delete this product because it has been added to a cart/order.");
			
			return "redirect:/";	
		}

		redirectAttributes.addFlashAttribute("message",name+" has been removed.");
		return "redirect:/";		
	}
	
	
	
	
	@RequestMapping("/product/{id}")
	public ModelAndView showProductPage(@PathVariable(name = "id") int id,HttpSession session) {
		ModelAndView mav = new ModelAndView("product-details");
		Product product = service.get(id);
		
		
		@SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
			
	  //      mav.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());
	    
		mav.addObject("product", product );
		mav.addObject("notesSession", userdata!=null? userdata:new ArrayList<>());
		if (session.getAttribute("NOTES_SESSION") == null) {							
			mav.addObject("sizeCart",cartservice.sizeCart2(session));
		}
		else
        {mav.addObject("sizeCart", cartservice.sizeCart(session));}
		return  mav;
	}
	

}
