package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
public class UsersRestController { // *** /!\ IN CONSTRUCTION, FOR YOUR SAFETY, WEAR A HELMET /!\ ***

	@Autowired
	private UserRepository repository;

	// ********************** POST ********************
	@RequestMapping(value = "/api/users/new", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@PathVariable String userInternalName) {
		return null;
	}

	// ********************** FIND ********************

	// Find user by internalName
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUser(@PathVariable String userInternalName) {
		User user = repository.findByInternalName(userInternalName);
		if (user != null)
			return new ResponseEntity<>(user, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// Find teacher by internalName
	@RequestMapping(value = "/api/teachers/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findTeacher(@PathVariable String userInternalName) {
		User user = repository.findByInternalName(userInternalName);
		if ((user != null) && (!user.isStudent()))
			return new ResponseEntity<>(user, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// Find student by internalName
	@RequestMapping(value = "/api/students/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findStudent(@PathVariable String userInternalName) {
		User user = repository.findByInternalName(userInternalName);
		if ((user != null) && (user.isStudent()))
			return new ResponseEntity<>(user, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// User in course, all user, all teachers, all students, all teacher

}
