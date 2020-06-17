package com.magroun.Ecommerce;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {
@Autowired
ContactService contactservice;

@Autowired
EmailService emailservice;
@Autowired
CartService cartservice;
	
	@RequestMapping("contact")
	public String showConntactPage(Model model, HttpSession session) {
		Contact contact = new Contact();
		model.addAttribute("contact", contact);
		
		   @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
	        model.addAttribute("notesSession", userdata!=null? userdata:new ArrayList<>());	
			if (session.getAttribute("NOTES_SESSION") == null) {							
				model.addAttribute("sizeCart",cartservice.sizeCart2(session));
			}
			else
	        {model.addAttribute("sizeCart", cartservice.sizeCart(session));}
	return "contact";
	}	
	
	@RequestMapping(value = "/savemessage", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("contact") Contact contact,RedirectAttributes redirectAttributes) {
	contactservice.save(contact);
	emailservice.sendMsg(contact);
		redirectAttributes.addFlashAttribute("message","your message has been sent successfully");
		return "redirect:/contact";
	}
}
