package com.example.demo.restControllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course.Course;
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

	// ********************** POST ********************
	@RequestMapping(value = "/api/users", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@RequestParam String username, @RequestParam String password,
			@RequestParam String repeatPassword, @RequestParam String userMail) { /* admin to add teachers */

		Boolean admin = null;

		User user = sessionUserComponent.getLoggedUser();/* Only for admin */
		if (user == null)
			admin = false;
		else if (user.isAdmin())
			admin = true;
		if (admin != null) {

			User newUser = userService.checkUser(username, password, repeatPassword, userMail, admin);
			if (newUser != null)
				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		}
		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// ********************** PUT ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateBook(@PathVariable String userInternalName, @RequestBody User user) {

		if (userInternalName.equals(sessionUserComponent.getLoggedUser().getInternalName())) {
			/* I can only modify the data of the logged user */

			User updatedUser = userService.updateUser(userInternalName, user);

			if (updatedUser != null) {
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

	// ********************** FIND ********************

	// Find user by internalName
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUser(@PathVariable String userInternalName) {

		User user = repository.findByInternalName(userInternalName);
		if (user != null)
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// ********************** FIND IN COURSE ********************
	@JsonView(User.BasicUser.class)
	@RequestMapping(value = "/api/users/{courseID}/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUserInCourse(@PathVariable String userInternalName, @PathVariable Long courseID) {
		User user = repository.findByInternalName(userInternalName);
		if (user != null) {
			for (Course c : user.getInscribedCourses())
				if (c.getCourseID() == courseID)
					return new ResponseEntity<>(user, HttpStatus.FOUND);
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
			return new ResponseEntity(pageUser, HttpStatus.FOUND);
		else
			return new ResponseEntity(pageUser, HttpStatus.NOT_FOUND);

	}

	/* Get profile photo */
	@RequestMapping(value = "/api/users/img/{userInternalName}", method = RequestMethod.GET)
	public void getProfilePhoto(@PathVariable String userInternalName,
			HttpServletResponse res) throws IOException {
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
	public ResponseEntity<User> uploadProfilePhoto(Model model, User userUpdated, @PathVariable String userInternalName,
			@RequestParam("profileImage") MultipartFile file) {

		User user = repository.findByInternalName(userInternalName);
		User loggedUser = sessionUserComponent.getLoggedUser();

		if (user != null && user.equals(loggedUser)) {
			/* Image uploading controll. If a profile image exists, it is overwritten */
			/* If there is not file the imageName wont change */

			user = userService.saveImg(file, user);
			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return null;
		}
	}

}
