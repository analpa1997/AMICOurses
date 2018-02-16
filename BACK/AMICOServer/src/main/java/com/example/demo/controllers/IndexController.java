package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.user_package.UserRepository;
import com.example.demo.user_package.User;

@Controller
public class IndexController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	/*Introduce Mock data*/
	@PostConstruct
	public void init() {
		
		List <Course> courses = new ArrayList<>();
		
		List <User> users = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			courses.add(new Course("Introduction to AI", "English", null, null,
				"If you want to learn all about AI, this is our course",
				"https://i.blogs.es/09f8d5/650_1200/450_1000.jpg"));
	
			courses.add(new Course("AI: Advanced Tips", "English", null, null,
				"If you want to learn all about AI, this is our course",
				"http://www.globalmediait-cr.com/wp-content/uploads/2016/08/BBVA-OpenMind-El-futuro-de-la-inteligencia-artificial-y-la-cibern%C3%A9tica.jpg"));
		}
		
		for (int i = 0; i < 5; i++) {
			users.add(new User("User-" + i, "pass", "hola" + i +"@mail.com", true));
			
			courses.get(i%2).getInscribedUsers().add(users.get(i));
		}
		
		userRepository.save(users);
		courseRepository.save(courses);
		
		
		/*for (int i = 0; i < 5; i++) {
			
		}*/
	}

	@RequestMapping("/")
	public String allCourses(Model model) {
		List<Course> c1 = courseRepository.findByName("AI: Advanced Tips");
		List<Course> c2 = courseRepository.findByName("Introduction to AI");
		List<Course> c = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			c.add(c1.get(i));
			c.add(c2.get(i));
		}
		
		/* Test Query. It should retrieve "Introduction to AI" */
		System.out.println(userRepository.findByUsername("User-0").get(0).getInscribedCourses().get(0).getName());
		
		
		model.addAttribute("courseList", c);
		return "HTML/index";
	}

	// @RequestMapping("/searchByName")
	/*
	 * public String courseByName(Model model) {
	 * 
	 * }
	 */

}
