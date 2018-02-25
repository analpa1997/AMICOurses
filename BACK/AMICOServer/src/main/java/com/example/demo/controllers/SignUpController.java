package com.example.demo.controllers;

import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class SignUpController {
	@Autowired UserRepository userRepository;
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("wrongData",false);
		return "HTML/LogIn/signup";
	}
	
	@RequestMapping(value="/registered", method= RequestMethod.POST)
	public String registered(Model model, @RequestParam String username, @RequestParam String userMail,
			@RequestParam String password, @RequestParam String repeatPassword) {
		
		if(passwordMatch(password, repeatPassword) && correctName(username)&& isValidEmailAddress(userMail)
				&& (userRepository.findByUsername(username) == null) &&  (userRepository.findByUserMail(userMail) == null)){
			User u = new User(username, password, userMail, true);
			userRepository.save(u);
			
			return "HTML/LogIn/login";
			
		}
		else {
			model.addAttribute("wrongData",true);
			return "HTML/LogIn/signup";
		}
	}
	
	static boolean passwordMatch(String s1, String s2) {
		return (Objects.equals(s1, s2)&&s1.length()>7 && s1.length()<15);
	}
	
	static boolean correctName(String s) {
		return(s.length()>4 && s.length()<16 && s.matches("^[a-zA-Z0-9_-]*$"));
	}
	
	
	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
}
