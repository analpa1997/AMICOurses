package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.course_package.Course;
import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;

@Controller
public class CourseInformationController {
	
	private Course course;
			
	@Autowired
	private SubjectRepository sujectRepository;
	
	
	@RequestMapping("/html/courseInformation/course/{courseID}")
	public String course(Model model, Course c) {
		
		course.setCourseID(c.getCourseID());
				
		model.addAttribute(c.getCourseDescription());
		return "HTML/courseInformation/course";
	}

	@RequestMapping("/html/courseInformation/subjects/{courseID}")
	public String subjects(Model model) {
		
		List<Subject> subjects = new ArrayList<>();
		subjects = SubjectRepository.findByCourseID(course.getCourseID());
				
		model.addAttribute("subjectList", subjects);
		return "HTML/courseInformation/subjects";
	}
	
}
