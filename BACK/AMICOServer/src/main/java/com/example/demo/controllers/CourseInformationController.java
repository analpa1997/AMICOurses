package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.skill_package.Skill;
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.SessionUserComponent;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class CourseInformationController {

	private Course course = null;
	private String userName;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	

	// For course main page description
	@RequestMapping("/course/{internalName}")
	public String course(Model model, @PathVariable String internalName) {
		
		User u = null;
		
		// Check for the logged user
		u = sessionUserComponent.getLoggedUser();
		if (u == null)
			userName = "amico";  // para que funcione
		else 
			userName = u.getInternalName();
		
		// get the course information by course internal name
		course = courseRepository.findByInternalName(internalName);
		System.out.println(internalName);
		System.out.println(userName);

		// set the course  attributes to show
		model.addAttribute("courseName", course.getName());
		model.addAttribute("courseDescription", course.getCourseDescription());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		String startDateString = dateFormat.format(course.getStartDate());
		String endDateString = dateFormat.format(course.getEndDate());

		model.addAttribute("startDateString", startDateString);
		model.addAttribute("endDateString", endDateString);
		model.addAttribute("urlImage", course.getUrlImage());
		//model.addAttribute("nameInternal", internalName);

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
		model.addAttribute("urlImage", course.getUrlImage());
		model.addAttribute("nameInternal", internalName);
		return "HTML/CourseInformation/subjects";
	}

	@RequestMapping("/course/{internalName}/skills")
	public String skills(Model model, @PathVariable String internalName) {

		course = courseRepository.findByInternalName(internalName);
		List<Skill> skill = course.getSkills();

		System.out.println("# skills" + " " + skill.size());

		model.addAttribute("skills", skill);
		model.addAttribute("urlImage", course.getUrlImage());
		model.addAttribute("nameInternal", internalName);

		return "HTML/CourseInformation/skills";
	}

	@RequestMapping("/course/{internalName}/add")
	public ModelAndView addCourseToUser(Model model, @PathVariable String internalName) {

		int courseCounter;
		List<User> u = null;
		List<Course> c = null;
		
		if (userName.equals("anonymous"))
			return new ModelAndView("redirect:/");
		
		User user = userRepository.findByUsername(userName); // get all user information by userName
		if (course.getInscribedUsers().size() > 0)
			u = course.getInscribedUsers(); // get list of user subscribed to course
		else
/***** HEAD  
			u = new ArrayList <>(); // initialize array to add user
		
		if (user.getInscribedCourses().size() > 0)
       	 	c = user.getInscribedCourses(); // get the list of courses to which the user is subscribed
		else 
			c = new ArrayList <>(); // initialize array to add users
		
       	System.out.println("# cursos inscritos" + " " + c.size());
       	System.out.println("# usuarios del curso" + " " + course.getInscribedUsers().size());
       	
       	// Check if user is already subscribed to the course
       	Iterator<Course> itr = c.iterator();
        courseCounter = 0;
        while(itr.hasNext() ) {
           Course cr = itr.next();
           System.out.println(" curso " + " " + course.getCourseID() + " cursoIterator " + "" + cr.getCourseID());
           if (cr.getCourseID() == course.getCourseID()) {
        	      courseCounter ++;
           }
        }
        System.out.println("courseCounter : " + courseCounter);	
        // if user not subscribed to the course, inscribe him
        	if (courseCounter != 1) {
        		 	   						
			System.out.println("course : " + course.getName() + " No lo tiene");		
			
			c.add(course);			
END HEAD ***/
			u = new ArrayList<>(); // initialize array to add user

		if (user.getInscribedCourses().size() > 0)
			c = user.getInscribedCourses(); // get the list of courses to which the user is subscribed
		else
			c = new ArrayList<>(); // initialize array to add users

		System.out.println("# cursos inscritos" + " " + c.size());
		System.out.println("# usuarios del curso" + " " + course.getInscribedUsers().size());

		// Check if user is already subscribed to the course
		Iterator<Course> itr = c.iterator();
		courseCounter = 0;
		while (itr.hasNext()) {
			Course cr = itr.next();
			System.out.println(" curso " + " " + course.getCourseID() + " cursoIterator " + "" + cr.getCourseID());
			if (cr.getCourseID() == course.getCourseID()) {
				courseCounter++;
			}
		}
		System.out.println("courseCounter : " + courseCounter);
		// if user not subscribed to the course, register him
		if (courseCounter != 1) {

			System.out.println("course : " + course.getName() + " No lo tiene");

			c.add(course);
/*** >>>>>>> 19411b2f2870148992f19ee82443e327540cfa06 ***/
			user.setInscribedCourses(c);
			userRepository.save(user); // update user

			u.add(user);
			course.setInscribedUsers(u);
			course.setNumberOfUsers(course.getNumberOfUsers() + 1);
			courseRepository.save(course);// update courses

		}

		user = userRepository.findByUsername(userName);
		System.out.println("# cursos inscritos" + " " + user.getInscribedCourses().size());
		
		return new ModelAndView("redirect:/profile/" + user.getUsername());
		
	}

}
