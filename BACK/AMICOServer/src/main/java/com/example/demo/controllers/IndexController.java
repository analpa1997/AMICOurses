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
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;

	/* Introduce Mock data */
	@PostConstruct
	public void init() {
		/*
		 * for (int i = 0; i < 5; i++) {
		 * 
		 * }
		 */
	}

	@RequestMapping("/")
	public String allData(Model model) {

		/* Habria que paginar la busqueda */
		List<Course> c = new ArrayList<>();
		c.addAll(courseRepository.findAll());

		/* Test Query. It should retrieve "Introduction to AI" */
		List<Course> queryCourses = userRepository.findByUsername("student-0").getInscribedCourses();
		for (Course course : queryCourses) {
			System.out.println("Course name : " + course.getName() + " course id: " + course.getCourseID()
					+ " \n skills: " + course.getSkills() + " \n subjects " + course.getSubjects());
		}

		List<Subject> subjects = new ArrayList<>();
		for (Course course : courseRepository.findAll()) {
			for (Subject s : course.getSubjects()) {
				if (!subjects.contains(s))
					subjects.add(s);
			}
		}

		model.addAttribute("courseList", c);
		model.addAttribute("subjectsList", subjects);
		return "HTML/index";
	}

	@RequestMapping("/login.html")
	public String login(Model model) {
		return "HTML/LogIn/login";
	}

	@RequestMapping("/signup.html")
	public String signup(Model model) {
		return "HTML/LogIn/signup";
	}

	// @RequestMapping("/searchByName")
	/*
	 * public String courseByName(Model model) {
	 * 
	 * }
	 */

}
