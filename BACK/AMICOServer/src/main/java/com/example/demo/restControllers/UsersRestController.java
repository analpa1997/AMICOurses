package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;

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
	@RequestMapping(value = "/{userInternalName}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateBook(@PathVariable String userInternalName, @RequestBody User updatedUser,

			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "repeatPassword", required = false) String repeatPassword,
			@RequestParam(value = "userMail", required = false) String userMail,
			@RequestParam(value = "userFirstName", required = false) String userFirstName,
			@RequestParam(value = "userLastName", required = false) String userLastName,
			@RequestParam(value = "userAddress", required = false) String userAddress,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "country", required = false) String country,
			@RequestParam(value = "phoneNumber", required = false) Integer phoneNumber,
			@RequestParam(value = "interests", required = false) String interests,
			@RequestParam(value = "urlProfileImage", required = false) String urlProfileImage) {

		updatedUser = userService.updateUser(userInternalName, username, password, repeatPassword, userMail,
				userFirstName, userLastName, userAddress, city, country, phoneNumber, interests, urlProfileImage);

		if (updatedUser != null) {
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// ********************** DELETE ********************
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
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUser(@PathVariable String userInternalName) {

		User user = repository.findByInternalName(userInternalName);
		if (user != null)
			return new ResponseEntity<>(user, HttpStatus.FOUND);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// ********************** FIND IN COURSE ********************
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

	@RequestMapping(value = "/api/users/all", method = RequestMethod.GET)
	public ResponseEntity<Page<User>> allUsers(Pageable pages,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "isStudent", required = false) Boolean isStudent) {
		Page<User> pageUser;
		if (isStudent = null)
			pageUser = repository.findAll(new PageRequest(page, 10));
		else
			pageUser = repository.findByIsStudent(isStudent, new PageRequest(page, 10));

		if (pageUser != null)
			return new ResponseEntity(pageUser, HttpStatus.FOUND);
		else
			return new ResponseEntity(pageUser, HttpStatus.NOT_FOUND);

	}

}
