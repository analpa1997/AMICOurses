package com.example.demo.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.skill.Skill;
import com.example.demo.subject.Subject;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Controller
public class CourseInformationController {

	private Course course = null;
	private String userName;
	private User usr = null;
	private boolean isAnStudent=false;
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	

	// For course main page description
	@RequestMapping("/course/{internalName}")
	public String course(Model model, @PathVariable String internalName) {
				
		// Check for the logged user
		usr = sessionUserComponent.getLoggedUser();
		
		if (usr == null)
			userName = "amico";
		else {
			userName = usr.getInternalName();
			isAnStudent = usr.isStudent();
		}
		// get the course information by course internal name
		course = courseRepository.findByInternalName(internalName);
		
		// set the course  attributes to show
		model.addAttribute("courseName", course.getName());
		model.addAttribute("courseDescription", course.getCourseDescription());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		String startDateString = dateFormat.format(course.getStartDate());
		String endDateString = dateFormat.format(course.getEndDate());

		model.addAttribute("startDateString", startDateString);
		model.addAttribute("endDateString", endDateString);
		model.addAttribute("urlImage", course.getOriginalName());
		model.addAttribute("courseID", course.getCourseID());

		return "HTML/courseInformation/course";
	}

	@RequestMapping("/course/{internalName}/subjects")
	public String subjects(Model model, @PathVariable String internalName) {

		System.out.println(internalName);
		course = courseRepository.findByInternalName(internalName);
		List<Subject> subject = course.getSubjects();

		System.out.println("# Subject" + " " + subject.size());

		model.addAttribute("courseName", course.getName());
		model.addAttribute("subjects", subject);
		model.addAttribute("urlImage", course.getOriginalName());
		model.addAttribute("nameInternal", internalName);
		return "HTML/CourseInformation/subjects";
	}

	@RequestMapping("/course/{internalName}/skills")
	public String skills(Model model, @PathVariable String internalName) {

		course = courseRepository.findByInternalName(internalName);
		List<Skill> skill = course.getSkills();

		System.out.println("# skills" + " " + skill.size());

		model.addAttribute("skills", skill);
		model.addAttribute("urlImage", course.getOriginalName());
		model.addAttribute("nameInternal", internalName);

		return "HTML/CourseInformation/skills";
	}

	@RequestMapping("/course/{internalName}/add")
	public ModelAndView addCourseToUser(Model model, @PathVariable String internalName) {

		int courseInscribedCounter;
		int courseCompletedCounter;
		List<User> uInscribedList = null;   // Users list
		List<Course> cInscribedUsers = null; // Courses list
		List<Course> cCompletedList = null; 
		ModelAndView mv = null;
				
		
		// Check the users to allow or not the course registration 
		if (userName.equals("anonymous")) {
			//"You must be logged-in to subscribe to a course. Return to main page and log-in");
			// return to /course/{internalName}
		}
		else {
			if (!isAnStudent) {
				// "Teachers cannot register in courses"
				// return to /course/{internalName}
			}
		}

		
		User user = userRepository.findByUsername(userName); // get all user information by userName
		
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
		
		System.out.println("# cursos inscritos" + " " + cInscribedUsers.size());

		courseInscribedCounter = 0;
		while (courseInscribedCounter < cInscribedUsers.size() && cInscribedUsers.get(courseInscribedCounter).getCourseID() != course.getCourseID())
			courseInscribedCounter++;
		
		System.out.println("courseCounter : " + courseInscribedCounter);
		
		courseCompletedCounter = 0;
		while (courseCompletedCounter < cCompletedList.size() && cCompletedList.get(courseCompletedCounter).getCourseID() != course.getCourseID())
			courseCompletedCounter++;
		
		System.out.println("courseCounter : " + courseCompletedCounter);
		
		// if user not subscribed to the course, register him
		if (courseInscribedCounter == cInscribedUsers.size() && courseCompletedCounter == cCompletedList.size()) {

			System.out.println("course : " + course.getName() + " No lo tiene");

			cInscribedUsers.add(course);
			user.setInscribedCourses(cInscribedUsers);
			userRepository.save(user); // update user

			uInscribedList.add(user);
			course.setInscribedUsers(uInscribedList);
			course.setNumberOfUsers(course.getNumberOfUsers() + 1);
			courseRepository.save(course);// update courses
			return new ModelAndView("redirect:/profile/" + user.getUsername());

		}
		else {//" already registered in this course");
			return new ModelAndView("redirect:/course/{internalName}/").addObject("error", "You are already registered in this course"); 
			
		}
	}
	
	@RequestMapping("/courses/img/{courseID}")
	public void getProfileImage(@PathVariable Long courseID, HttpServletResponse res)
			throws FileNotFoundException, IOException {
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/image/courses/" + courseID + "/");

		Path image = FILES_FOLDER.resolve("course-" + courseID + ".jpg");

		res.setContentType("image/jpeg");
		res.setContentLength((int) image.toFile().length());
		FileCopyUtils.copy(Files.newInputStream(image), res.getOutputStream());

	}

}
