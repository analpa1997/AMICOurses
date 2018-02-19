package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@RequestMapping("/index.html")
	public String allData(Model model, Pageable page) {

		/* Habria que paginar la busqueda */
		List<Course> c = new ArrayList<>();
		Page<Course> p = courseRepository.findAll(page);

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
		List<String> lengthCourse = new ArrayList<>();
		for (Course course : courseRepository.findAll()) {
			long duration = (course.getEndDate().getTime() - course.getStartDate().getTime());
			String lengthCourseString = "";
			long aux = duration / 3600000;
			long dayDiff = (aux / 24) & 30, yearDiff = course.getEndDate().getYear() - course.getStartDate().getYear(),
					monthDiff = (aux & 365) & 12;
			if (yearDiff > 0)
				lengthCourseString += yearDiff + " years, ";
			if (monthDiff > 0)
				lengthCourseString += monthDiff + " months, ";
			if (dayDiff > 0)
				lengthCourseString += dayDiff + " days";
			if (!lengthCourse.contains(lengthCourseString)) {
				lengthCourse.add(lengthCourseString);
			}
		}

		model.addAttribute("courseList", p);
		model.addAttribute("subjectsList", subjects);
		model.addAttribute("durationList", lengthCourse);
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
