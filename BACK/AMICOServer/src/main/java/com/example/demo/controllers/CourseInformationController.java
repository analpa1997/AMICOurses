package com.example.demo.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;
import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;

@Controller
public class CourseInformationController {
	
	private Course course;
			
	@Autowired
	private SubjectRepository sujectRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	
	@RequestMapping("/course/{courseID}")
	public String course(Model model, @PathVariable long courseID) {
		
		System.out.println("course" + courseID);
		course = courseRepository.findOne(courseID);
			
		model.addAttribute("courseDescription", course.getCourseDescription());
		return "HTML/courseInformation/course";
	}

	@RequestMapping("subjects")
	public String subjects(Model model) {
		
		List<Subject> subjects = course.getSubjects();
		
		Iterator<Subject> iter = subjects.iterator();
		while (iter.hasNext()) {
			Subject sub = iter.next();
			System.out.println("name" + sub.getName());
			model.addAttribute("name", sub.getName());
			System.out.println("description" + sub.getDescription());
			model.addAttribute("description", sub.getDescription());
		}
		
		return "HTML/CourseInformation/subjects";
	}
	
}
