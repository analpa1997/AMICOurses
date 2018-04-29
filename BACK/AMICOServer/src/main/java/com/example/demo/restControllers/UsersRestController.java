package com.example.demo.restControllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.skill.Skill;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class UsersRestController {

	@Autowired
	private UserRepository repository;
	@Autowired
	private SessionUserComponent sessionUserComponent;
	@Autowired
	private UserService userService;

	// ********************** GET ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/request", method = RequestMethod.GET)
	public Boolean[] response(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "userMail", required = true) String userMail) {
		Boolean errors[] = { false, false };
		if (repository.findByUsername(username) == null)
			errors[0] = true;
		if (repository.findByUserMail(userMail) == null)
			errors[1] = true;

		return errors;
	}

	/*
	 * @JsonView(User.BasicUser.class)
	 * 
	 * @RequestMapping(value = "/api/users", method = RequestMethod.POST) public
	 * ResponseEntity<User> newUser(@RequestParam String username, @RequestParam
	 * String password,
	 * 
	 * @RequestParam String repeatPassword, @RequestParam String userMail) {
	 * 
	 * Boolean admin = null;
	 * 
	 * User user = sessionUserComponent.getLoggedUser(); if (user == null) admin =
	 * false; else if (user.isAdmin()) admin = true; if (admin != null) {
	 * 
	 * User newUser = userService.checkUser(username, password, repeatPassword,
	 * userMail, admin); if (newUser != null) return new ResponseEntity<>(newUser,
	 * HttpStatus.CREATED); } return new
	 * ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
	// ********************** POST ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public User newUser(@RequestBody User user) { /* admin to add teachers */

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setUrlProfileImage("null");
		if (user.getUserFirstName() == null || user.getUserFirstName().isEmpty()) {
			user.setUserFirstName("");
		}
		
		if (user.getUserLastName() == null || user.getUserLastName().isEmpty()) {
			user.setUserLastName("");
		}
		
		user.setUserAddress("");
		user.setCity("");
		user.setCountry("");
		user.setPhoneNumber("00000000");
		user.setInterests("");
		user.setRoles(new ArrayList<>(Arrays.asList("ROLE_USER")));

		repository.save(user);

		return user;
	}

	// ********************** PUT ********************
	@JsonView(myProfile.class)
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable String userInternalName, @RequestBody User user) {
		if (sessionUserComponent.isLoggedUser()) {
			if (user.equals(sessionUserComponent.getLoggedUser())) {
				/* I can only modify the data of the logged user */

				User updatedUser = userService.updateUser(userInternalName, user);

				if (updatedUser != null) {
					System.out.println(updatedUser);
					return new ResponseEntity<>(updatedUser, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	}

	// ********************** DELETE ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.DELETE)
	public ResponseEntity<User> delUser(@PathVariable String userInternalName) {

		User user = sessionUserComponent.getLoggedUser();/* Only for admin */
		if (user != null && user.isAdmin()) {
			User userDel = userService.deleteUser(userInternalName);
			if (user != null)
				return new ResponseEntity<>(userDel, HttpStatus.OK);
			else
				return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	// My profile
	interface myProfile extends User.BasicUser, User.ExtendedUser, Course.BasicCourse, Skill.BasicSkill {
	}

	@JsonView(myProfile.class)
	@RequestMapping(value = "/api/users/myProfile", method = RequestMethod.GET)
	public ResponseEntity<User> myProfile() {
		if (sessionUserComponent.isLoggedUser()) {
			User user = repository.findByInternalName(sessionUserComponent.getLoggedUser().getInternalName());
			if (user != null)
				return new ResponseEntity<>(user, HttpStatus.OK);
			else
				return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	}

	// ********************** FIND ********************

	// Find user by internalName
	@JsonView(myProfile.class)
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public User findUser(@PathVariable String userInternalName) {

		User user = repository.findByInternalName(userInternalName);
		if (user != null)
			return user;
		else
			return null;
	}

	// ********************** FIND IN COURSE ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/{courseID}/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUserInCourse(@PathVariable String userInternalName, @PathVariable Long courseID) {
		User user = repository.findByInternalName(userInternalName);
		if (user != null) {
			for (Course c : user.getInscribedCourses())
				if (c.getCourseID() == courseID)
					return new ResponseEntity<>(user, HttpStatus.OK);
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// ********************** RETURN ALL ********************

	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/all", method = RequestMethod.GET)
	public ResponseEntity<Page<User>> allUsers(Pageable pages,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "isStudent", required = false) Boolean isStudent) {
		Page<User> pageUser;
		if (isStudent == null)
			pageUser = repository.findAll(new PageRequest(page, 10));
		else
			pageUser = repository.findByIsStudent(isStudent, new PageRequest(page, 10));

		if (pageUser != null)
			return new ResponseEntity(pageUser, HttpStatus.OK);
		else
			return new ResponseEntity(pageUser, HttpStatus.NOT_FOUND);
	}

	/* Get profile photo */
	@RequestMapping(value = "/api/users/img/{userInternalName}", method = RequestMethod.GET)
	public void getProfilePhoto(@PathVariable String userInternalName, HttpServletResponse res) throws IOException {
		User user = repository.findByInternalName(userInternalName);
		if (user != null) {

			try {
				Path image = userService.getImgPath(user);
				res.setContentType("image/jpeg");
				res.setContentLength((int) image.toFile().length());
				FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

			} catch (IOException exception) {
				res.sendError(404);
				exception.printStackTrace();
			}

		} else {
			res.sendError(404);
		}
	}

	/* Post a profile photo */
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/img/{userInternalName}", method = RequestMethod.PUT)
	public ResponseEntity<User> uploadProfilePhoto(@PathVariable String userInternalName,
			@RequestParam("profileImage") MultipartFile file) {

		User user = repository.findByInternalName(userInternalName);
		User loggedUser = sessionUserComponent.getLoggedUser();

		if (user != null && user.equals(loggedUser)) {
			/* Image uploading controll. If a profile image exists, it is overwritten */

			user = userService.saveImg(file, user);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
