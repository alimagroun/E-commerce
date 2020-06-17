package com.magroun.Ecommerce;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
@Autowired UserService userservice;
@Autowired CartService cartservice;
	
	@RequestMapping("myaccount")
	public ModelAndView showEditMyAccountPage(HttpSession session) {
		ModelAndView mav = new ModelAndView("edit_user");
        @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		
		User user =userservice.get(Long.parseLong(userdata.get(0)));
		mav.addObject("user", user);
		mav.addObject("sizeCart", cartservice.sizeCart(session));
		mav.addObject("notesSession", userdata);
		return  mav;
	}	
	@RequestMapping("changepassword")
	public String viewChangePasswordPage(Model model, HttpSession session) {			
	        model.addAttribute("sizeCart", cartservice.sizeCart(session));
			@SuppressWarnings("unchecked")
			List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		    model.addAttribute("notesSession", userdata);
		return "edit_password";
	}	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changep(@RequestParam("oldpassword") String oldpassword,@RequestParam("password") String password,HttpSession session,RedirectAttributes redirectAttributes) {
		
        @SuppressWarnings("unchecked")
		List<String> userdata = (List<String>) session.getAttribute("NOTES_SESSION");
		User user = userservice.get(Long.parseLong(userdata.get(0)));
		if(!oldpassword.equals(user.getPassword())) {
			redirectAttributes.addFlashAttribute("oldpassword", "Incorrect old password");
			return "redirect:/changepassword";

		} else {
			user.setPassword(password);
			userservice.save(user);
			redirectAttributes.addFlashAttribute("passwordchanged", "Password changed successfully!");
			return "redirect:/changepassword";
			
		}
	
			
	
	}
	
}
