package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class CustomErrorController implements ErrorController {
	@Autowired
	CartService cartservice;

	@Override
	public String getErrorPath() {
		
	return	"error";
	}
	@GetMapping("error")
	public String handleError(Model model,HttpServletRequest request, HttpSession session) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	System.out.println("Error with status code " + status + " happened. Support! Do something about it!");
		   @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
	        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());	
			if (session.getAttribute("NOTES_SESSION") == null) {							
				model.addAttribute("sizeCart",cartservice.sizeCart2(session));
			}
			else
	        {model.addAttribute("sizeCart", cartservice.sizeCart(session));}
	 
			return "error";
	}
	

	
	
	
	
	
	
	
	
}
