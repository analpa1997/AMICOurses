package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
public class UsersRestController { // *** /!\ IN CONSTRUCTION, FOR YOUR SAFETY, WEAR A HELMET /!\ ***

	@Autowired
	private UserRepository repository;

	// ********************** POST ********************
	@RequestMapping(value = "/api/users/new", method = RequestMethod.POST)
	public ResponseEntity<User> newUser(@RequestParam String username, @RequestParam String password,
			@RequestParam String userMail, @RequestParam boolean isStudent) {
		User user = new User(username, password, userMail, isStudent);

		/* comprobar parametros correctos */

		repository.save(user);
		if (user != null)
			return new ResponseEntity<>(user, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// ********************** FIND ********************

	// Find user by internalName
	@RequestMapping(value = "/api/users/{userInternalName}", method = RequestMethod.GET)
	public ResponseEntity<User> findUser(@PathVariable String userInternalName, @RequestParam boolean isStudent) {// null
																													// to
																													// anyone
		User user = repository.findByInternalName(userInternalName);
		if (user != null)
			if (user.isStudent() == isStudent)// Student or Teacher
				return new ResponseEntity<>(user, HttpStatus.OK);
			else // isStudent==null-> AnyOne
				return new ResponseEntity<>(user, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// ********************** FIND IN COURSE ********************
	@RequestMapping(value = "/api/courses/{courseID}/{userInternalName}", method = RequestMethod.GET)
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
	/*
	 * @RequestMapping(value = "/api/users/all", method = RequestMethod.GET) public
	 * ResponseEntity<Page<User>> allUsers(Pageable pages){ Page<User> pageUser =
	 * repository.findAll(new PageRequest(0, 0));
	 * 
	 * }
	 */

}
