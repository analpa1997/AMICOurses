package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;

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

	@RequestMapping("/subjects/{internalName}")
	public String subjects(Model model, @PathVariable String internalName) {

		List<String> sName = new ArrayList<>();

		course = courseRepository.findByInternalName(internalName);
		List<Subject> subject = course.getSubjects();

		/*
		 * Iterator<Subject> iter = subjects.iterator();
		 * 
		 * while (iter.hasNext()) { Subject s = new Subject(); s=iter.next();
		 * sName.add(s.getName()); }
		 */
		System.out.println("# Subject" + " " + subject.size());

		model.addAttribute("subjects", subject);
		model.addAttribute("courseName", internalName);

		return "HTML/CourseInformation/subjects";
	}

}
