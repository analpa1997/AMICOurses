package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.course_package.Course;

@Controller
public class CourseInformationController {
		
	
	@RequestMapping("/html/courseInformation/course/{courseID}")
	public String courseInformation(Model model, Course course) {
				
		model.addAttribute("courseDescription");
		return "courseInformation/course";
	}

	
}
