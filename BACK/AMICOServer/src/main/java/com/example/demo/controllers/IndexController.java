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
		List<Course> queryCourses = userRepository.findByUsername("User-0").getInscribedCourses();
		for (Course course : queryCourses) {
			System.out.println("Course name : " + course.getName() + " course id: " + course.getCourseID());
		}
		
		
		
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
