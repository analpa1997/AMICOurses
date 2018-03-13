package com.example.demo.restControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.skill.Skill;
import com.example.demo.subject.Subject;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/api/courses")
public class CourseRestController {

	interface CourseBasicInformation extends Course.BasicInformation {
	}

	interface CourseInformation extends Course.BasicInformation, Course.UserInformation, User.BasicInformation {
	}

	interface SubjectsDetail
			extends Subject.SubjectsBasicInformation, Subject.CourseBasicInformation, Course.BasicInformation {
	}

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	private String message;

	@RequestMapping(value = { "/id/{courseID}/", "/name/{internalName}/" }, method = RequestMethod.PUT)
	public ResponseEntity<String> addCourseToUser(Model model, @PathVariable String internalName) {

		Course course;
		User user = null;
		int courseInscribedCounter;
		int courseCompletedCounter;
		List<User> uInscribedList = null; // Users list
		List<Course> cInscribedUsers = null; // Courses list
		List<Course> cCompletedList = null;

		// check if the visitor is registered
		if (!sessionUserComponent.isLoggedUser()) { // not registered
			message = "To register for a course it is necessary to be logged into the system. Press AMICOURSES to return to the main screen and be able to register in the system ";
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else { // registered. Get the user data
			course = courseRepository.findByInternalName(internalName);
			user = sessionUserComponent.getLoggedUser();
		}

		if (course.getInscribedUsers().size() > 0)
			uInscribedList = course.getInscribedUsers(); // get list of user subscribed to course
		else
			uInscribedList = new ArrayList<>(); // initialize array to add user

		if (user.getInscribedCourses().size() > 0)
			cInscribedUsers = user.getInscribedCourses(); // get the list of courses to which the user is subscribed
		else
			cInscribedUsers = new ArrayList<>(); // initialize array to add users

		if (user.getCompletedCourses().size() > 0)
			cCompletedList = user.getCompletedCourses(); // get the list of courses to which the user is subscribed
		else
			cCompletedList = new ArrayList<>(); // initialize array to add users

		courseInscribedCounter = 0;
		while (courseInscribedCounter < cInscribedUsers.size()
				&& cInscribedUsers.get(courseInscribedCounter).getCourseID() != course.getCourseID())
			courseInscribedCounter++;

		courseCompletedCounter = 0;
		while (courseCompletedCounter < cCompletedList.size()
				&& cCompletedList.get(courseCompletedCounter).getCourseID() != course.getCourseID())
			courseCompletedCounter++;

		if (courseCompletedCounter != cCompletedList.size())
			message = "You have already completed this course.";
		else if (courseInscribedCounter != cInscribedUsers.size())
			message = "You are already registered in this course.";
		else { // user can register in the course

			cInscribedUsers.add(course);
			user.setInscribedCourses(cInscribedUsers);
			userRepository.save(user); // update user

			uInscribedList.add(user);
			course.setInscribedUsers(uInscribedList);
			course.setNumberOfUsers(course.getNumberOfUsers() + 1);
			courseRepository.save(course);// update courses

			// go to user profile

			message = "course Added";
			return new ResponseEntity<>(message, HttpStatus.OK);

		}

		// go to same page to show the message
		return new ResponseEntity<>(message, HttpStatus.OK);

	}

	@JsonView(CourseInformation.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<List<Course>> allCourses(Pageable pages,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sort", defaultValue = "courseID") String nameField,
			@RequestParam(value = "type", defaultValue = "all") String type,
			@RequestParam(value = "name", defaultValue = "") String nameCourse) {
		Page<Course> pageCourse;
		if (!nameField.equals("")) {
			if (nameField.equals("numberOfUsers"))
				if (type.equals("all"))
					if (nameCourse.equals(""))
						pageCourse = courseRepository
								.findAll(new PageRequest(page, 10, Sort.Direction.DESC, nameField));
					else
						pageCourse = courseRepository.findByInternalNameContaining(nameCourse,
								new PageRequest(page, 10, Sort.Direction.DESC, nameField));
				else if (nameCourse.equals(""))
					pageCourse = courseRepository.findByType(type,
							new PageRequest(page, 10, Sort.Direction.DESC, nameField));
				else
					pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse,
							new PageRequest(page, 10, Sort.Direction.DESC, nameField));
			else if (type.equals("all"))
				if (nameCourse.equals(""))
					pageCourse = courseRepository.findAll(new PageRequest(page, 10, Sort.Direction.ASC, nameField));
				else
					pageCourse = courseRepository.findByInternalNameContaining(nameCourse,
							new PageRequest(page, 10, Sort.Direction.ASC, nameField));
			else if (nameCourse.equals(""))
				pageCourse = courseRepository.findByType(type,
						new PageRequest(page, 10, Sort.Direction.ASC, nameField));
			else
				pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse,
						new PageRequest(page, 10, Sort.Direction.ASC, nameField));
		} else if (type.equals("all"))
			if (nameCourse.equals(""))
				pageCourse = courseRepository.findAll(new PageRequest(page, 10));
			else
				pageCourse = courseRepository.findByInternalNameContaining(nameCourse, new PageRequest(page, 10));
		else if (nameCourse.equals(""))
			pageCourse = courseRepository.findByType(type, new PageRequest(page, 10));
		else
			pageCourse = courseRepository.findByTypeAndInternalNameContaining(type, nameCourse,
					new PageRequest(page, 10));
		if (pageCourse != null) {
			List<Course> listCourse = pageCourse.getContent();
			return new ResponseEntity<>(listCourse, HttpStatus.OK);
		} else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@JsonView(CourseInformation.class)
	@RequestMapping(value = { "/id/{courseID}/", "/name/{internalName}/" }, method = RequestMethod.GET)
	public ResponseEntity<Course> oneCourse(@PathVariable(required = false) Long courseID,
			@PathVariable(required = false) String internalName) {
		Course course;
		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);
		if (course != null)
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = { "/id/{courseID}/skills/", "/name/{internalName}/skills/" }, method = RequestMethod.GET)
	public ResponseEntity<List<Skill>> skills(@PathVariable(required = false) String internalName,
			@PathVariable(required = false) Long courseID) {

		Course course = null;
		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);
		if (course != null) {

			List<Skill> skillList;
			skillList = course.getSkills();

			return new ResponseEntity<>(skillList, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@JsonView(SubjectsDetail.class)
	@RequestMapping(value = { "/id/{courseID}/subjects/",
			"/name/{internalName}/subjects/" }, method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> subjects(@PathVariable(required = false) String internalName,
			@PathVariable(required = false) Long courseID) {

		Course course = null;

		if (internalName == null)
			course = courseRepository.findOne(courseID);
		else
			course = courseRepository.findByInternalName(internalName);

		if (course != null) {

			List<Subject> subjectsList;
			subjectsList = course.getSubjects();

			return new ResponseEntity<>(subjectsList, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
