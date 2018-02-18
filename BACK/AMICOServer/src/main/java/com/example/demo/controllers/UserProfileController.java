
package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class UserProfileController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/profile/{username}")
	public String viewProfile(Model model, User user) {
		
		user = userRepository.findByUsername("amico");

		model.addAttribute("name", user.getUserFirstName());
		model.addAttribute("lastName", user.getUserLastName());
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("mail", user.getUserMail());
		model.addAttribute("address", user.getUserAddress());
		model.addAttribute("city", user.getCity());
		model.addAttribute("country", user.getCountry());
		model.addAttribute("phoneNumber", user.getPhoneNumber());
		model.addAttribute("urlImage", user.getUrlProfileImage());
		model.addAttribute("inscribedCourses", user.getInscribedCourses());
		model.addAttribute("completedCourses", user.getCompletedCourses());
		model.addAttribute("urlImage", user.getUrlProfileImage());
		model.addAttribute("role", user.getRole());
		model.addAttribute("isStudent", user.isStudent());
		

		return "userProfile";
	}
}