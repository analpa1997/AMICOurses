package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;

@Controller
public class SignUpController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("wrongData", false);
		return "HTML/LogIn/signup";
	}

	@RequestMapping(value = "/registered", method = RequestMethod.POST)
	public String registered(Model model, @RequestParam String username, @RequestParam String userMail,
			@RequestParam String password, @RequestParam String repeatPassword) {

		if (userService.checkUser(username, password, repeatPassword, userMail, false) != null)
			return "HTML/LogIn/login";
		else {
			String valError = "";
			if (username.equals(""))
				valError = "The username can't be empty";
			else if (password.equals(""))
				valError = "The password can't be empty";
			else if (userMail.equals(""))
				valError = "The userMail can't be empty";
			else if (!userService.passwordMatch(password, repeatPassword)) {
				if (!password.equals(repeatPassword))
					valError = "The passwords are different.";
				else if (password.length() <= 7)
					valError = "The password is too short.";
				else if (password.length() >= 15)
					valError = "The password is too long.";
			} else if (!userService.correctName(username)) {
				if (username.length() <= 4)
					valError = "The username is too short.";
				else if (username.length() >= 16)
					valError = "The username is too long.";
				else if (!username.matches("^[a-zA-Z0-9_-]*$"))
					valError = "The username only can contains letters, numbers, - or _.";
			} else if (!userService.isValidEmailAddress(userMail)) {
				valError = "The email address is not correct.";
			} else if (userRepository.findByUsername(username) != null)
				valError = "There is another user with that username.";
			else if (userRepository.findByUserMail(userMail) != null)
				valError = "There is another user with that email address.";
			model.addAttribute("errorMessage", valError);
			model.addAttribute("wrongData", true);
			return "HTML/LogIn/signup";
		}
	}
}
