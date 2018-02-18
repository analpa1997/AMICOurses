package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.user_package.UserRepository;
import com.example.demo.user_package.User;

@Controller
public class SubjectListController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/course/{courseName}/{userName}")
	public String allCourses(Model model, @PathVariable String userName, @PathVariable String courseName) {

		User user = userRepository.findByUsername(userName.replaceAll("-", " "));
		String InternalCourseName = courseName;
		if (user != null) {
			Course actualCourse = null;
			for (Course course : user.getInscribedCourses()) {
				if (course.getInternalName().equals(InternalCourseName)) {
					actualCourse = course;
				}
			}
			System.out.println("COUUUUUURSEEEEEEE" + actualCourse.getSubjects());
			if (actualCourse != null) {
				model.addAttribute("subjects", actualCourse.getSubjects());
				model.addAttribute("courseName", actualCourse.getName());
				model.addAttribute("courseInternalName", actualCourse.getInternalName());
				model.addAttribute("userInternalName", userName);
			}
			
			model.addAttribute("internalUserName" , user.getInternalName());
			
			
			model.addAttribute("teacher", !user.isStudent());
		}
			
		return "HTML/StudentCourses/student-course-overview";
	}


}
