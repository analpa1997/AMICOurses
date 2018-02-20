
package com.example.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
		model.addAttribute("internalName", user.getInternalName());

		return "HTML/Profile/userProfile";
	}
	
	//Requets from form
	@RequestMapping(value = "/profile/{username}/updated", method = RequestMethod.POST)
	public ModelAndView updated(Model model, User userUpdated, @PathVariable String username, @RequestParam("profileImage") MultipartFile file) {
		
		User user = userRepository.findByUsername(username);
		
		/*Image uploading controll. If a profile image exists, it is overwritten*/
		/* If there is not file the imageName wont change*/
		if (!file.isEmpty()) { 
			try {
				Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/image/users/" + user.getUserID() + "/");
				if (!Files.exists(FILES_FOLDER)) {
					Files.createDirectories(FILES_FOLDER);
				}
				
				String fileName = "profile-" + user.getUserID() + ".jpg";

				File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
				file.transferTo(uploadedFile);
				user.setUrlProfileImage(fileName);
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
		/* End of the image upload section*/
			
		user.setUserFirstName(userUpdated.getUserFirstName());
		user.setUserLastName(userUpdated.getUserLastName());
		user.setUsername(userUpdated.getUsername());
		user.setUserMail(userUpdated.getUserMail());
		user.setUserAddress(userUpdated.getUserAddress());
		user.setCity(userUpdated.getCity());
		user.setCountry(userUpdated.getCountry());
		user.setPhoneNumber(userUpdated.getPhoneNumber());
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
	
	@RequestMapping("/profileimg/{userInternalName}")
	public void getProfileImage (@PathVariable String userInternalName, HttpServletResponse res) throws FileNotFoundException, IOException{
		User user = userRepository.findByInternalName(userInternalName);
		Path FILES_FOLDER =  Paths.get(System.getProperty("user.dir"), "files/image/users/" + user.getUserID() + "/");
		
		Path image = FILES_FOLDER.resolve(user.getUrlProfileImage());
		
		if (!Files.exists(image)) {
			image =  Paths.get(System.getProperty("user.dir"), "files/image/users/default/default.jpg");
		}

		res.setContentType("image/jpeg");
		res.setContentLength((int) image.toFile().length());
		FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

	}
}