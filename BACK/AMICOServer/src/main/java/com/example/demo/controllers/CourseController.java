package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Course;
import com.example.demo.repositories.CourseRepository;

@Controller
public class CourseController {

	@Autowired
	private CourseRepository repository;

	@PostConstruct
	public void init() {
		for (int i = 0; i < 5; i++) {
			repository.save(new Course("Introduction to AI", "English", null, null,
					"If you want to learn all about AI, this is our course",
					"https://i.blogs.es/09f8d5/650_1200/450_1000.jpg"));
		}
		for (int i = 0; i < 5; i++) {
			repository.save(new Course("AI: Advanced Tips", "English", null, null,
					"If you want to learn all about AI, this is our course",
					"http://www.globalmediait-cr.com/wp-content/uploads/2016/08/BBVA-OpenMind-El-futuro-de-la-inteligencia-artificial-y-la-cibern%C3%A9tica.jpg"));
		}
	}

	@RequestMapping("/")
	public String allCourses(Model model) {
		List<Course> c1 = repository.findByCourseName("AI: Advanced Tips");
		List<Course> c2 = repository.findByCourseName("Introduction to AI");
		List<Course> c = new ArrayList();
		for (int i = 0; i < 5; i++) {
			c.add(c1.get(i));
			c.add(c2.get(i));
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
