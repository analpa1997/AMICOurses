package com.example.demo.restControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value = "/api/courseInformation/")
public class CourseInformationRestController {
	
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	private String message;

	@RequestMapping(value = { "/{internalName}" }, method = RequestMethod.GET)
	public ResponseEntity<Course> course(@PathVariable String internalName) {
	
		Course course = null;
			
		course = courseRepository.findByInternalName(internalName);
		if (course != null)
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = { "/{internalName}/Skills" }, method = RequestMethod.GET)
	public ResponseEntity<List<Skill>> skills(@PathVariable String internalName) {
	
		Course course = null;
			
		course = courseRepository.findByInternalName(internalName);
		if (course != null && !course.getSkills().isEmpty()) {
						
			List<Skill> skillList;
			skillList = course.getSkills();
			
			return new ResponseEntity<>(skillList, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	interface SubjectsDetail
	extends Subject.SubjectsBasicInformation, Subject.CourseBasicInformation, Course.BasicInformation{}
	
	@JsonView(SubjectsDetail.class)
	@RequestMapping(value = { "/{internalName}/Subjects" }, method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> subjects(@PathVariable String internalName) {
	
		Course course = null;
			
		course = courseRepository.findByInternalName(internalName);
		
		if (course != null && !course.getSubjects().isEmpty()) {
						
			List<Subject> subjectsList;
			subjectsList = course.getSubjects();
			
			return new ResponseEntity<>(subjectsList, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping("/{internalName}/add")
	public ResponseEntity<String> addCourseToUser(Model model, @PathVariable String internalName) {

		Course course;
		User user = null;
		int courseInscribedCounter;
		int courseCompletedCounter;
		List<User> uInscribedList = null; // Users list
		List<Course> cInscribedUsers = null; // Courses list
		List<Course> cCompletedList = null;
		
		// check if the visitor is registered
		if (!sessionUserComponent.isLoggedUser()) {  // not registered
			message = "To register for a course it is necessary to be logged into the system. Press AMICOURSES to return to the main screen and be able to register in the system ";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		else { // registered. Get the user data
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
		
		if (courseCompletedCounter != cCompletedList.size()) { // if user has completed the course. show message
			message = "You have already completed this course.";
		}
		else if (courseInscribedCounter != cInscribedUsers.size()) { // if user is inscribed in the course. show message
			message = "You are already registered in this course.";
			
		} else {  // user can register in the course
		
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
	
}
