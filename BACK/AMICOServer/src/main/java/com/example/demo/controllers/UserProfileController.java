
package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class UserProfileController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/profile/{username}")
	public String viewProfile(Model model, @PathVariable String username) {
		
		User user = userRepository.findByUsername(username);

		model.addAttribute("userFirstName", user.getUserFirstName());
		model.addAttribute("userLastName", user.getUserLastName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userMail", user.getUserMail());
		model.addAttribute("userAddress", user.getUserAddress());
		model.addAttribute("phoneNumber", user.getPhoneNumber());
		model.addAttribute("isStudent", user.isStudent());
		model.addAttribute("urlProfileImage", user.getUrlProfileImage());
		model.addAttribute("inscribedCourses", user.getInscribedCourses());
		model.addAttribute("completedCourses", user.getCompletedCourses());
		model.addAttribute("interests", user.getInterests());

		return "HTML/Profile/userProfile";
	}
	
	//Requets from form
	@RequestMapping("/profile/{username}/updated")
	public ModelAndView updated(Model model, User userUpdated, @PathVariable String username) {
		
		User user = userRepository.findByUsername(username);

		user.setUserFirstName(userUpdated.getUserFirstName());
		user.setUserLastName(userUpdated.getUserLastName());
		user.setUsername(userUpdated.getUsername());
		user.setUserMail(userUpdated.getUserMail());
		user.setUserAddress(userUpdated.getUserAddress());
		user.setCity(userUpdated.getCity());
		user.setCountry(userUpdated.getCountry());
		user.setPhoneNumber(userUpdated.getPhoneNumber());
		user.setUrlProfileImage(userUpdated.getUrlProfileImage());
		user.setInterests(userUpdated.getInterests());
		
		userRepository.save(user);
		
		return new ModelAndView("redirect:/profile/"+ user.getUsername());
	}
	
	//Requets to form
	@RequestMapping("/profile/{username}/update")
	public String update(Model model, @PathVariable String username) {
		
		User user = userRepository.findByUsername(username);

		model.addAttribute("interests", user.getInterests());
		model.addAttribute("userFirstName", user.getUserFirstName());
		model.addAttribute("userLastName", user.getUserLastName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userMail", user.getUserMail());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("userAddress", user.getUserAddress());
		model.addAttribute("role", user.getRole());
		model.addAttribute("isStudent", user.isStudent());
		model.addAttribute("urlProfileImage", user.getUrlProfileImage());
		model.addAttribute("city", user.getCity());
		model.addAttribute("country", user.getCountry());
		model.addAttribute("phoneNumber", user.getPhoneNumber());
		
		
		

		return "HTML/Profile/profile-update";
	}
}