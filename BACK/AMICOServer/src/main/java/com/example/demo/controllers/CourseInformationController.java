package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.skill_package.Skill;
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.UserRepository;
import com.example.demo.user_package.User;


@Controller
public class CourseInformationController {

	private Course course;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;
	

	// For course main page description
	@RequestMapping("/course/{internalName}/{userName}")
	public String course(Model model, 
			@PathVariable String internalName,
			@PathVariable String userName) {

		// get the course information by name
		course = courseRepository.findByInternalName(internalName);
		System.out.println(internalName);
        
		//set the course description attributes
		model.addAttribute("courseName", course.getName());
		model.addAttribute("courseDescription", course.getCourseDescription());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		String startDateString = dateFormat.format(course.getStartDate());
		String endDateString = dateFormat.format(course.getEndDate());
		
		model.addAttribute("startDateString", startDateString);
		model.addAttribute("endDateString", endDateString);
		model.addAttribute("urlImage", course.getUrlImage());
		model.addAttribute("nameInternal", internalName);
		
		if (userName.length() > 1);
			model.addAttribute("userName", userName);
		
		return "HTML/courseInformation/course";
	}

	@RequestMapping("/course/{internalName}/subjects")
	public String subjects(Model model, @PathVariable String internalName) {
		
		System.out.println(internalName);
		course = courseRepository.findByInternalName(internalName);
		List<Subject> subject = course.getSubjects();

		System.out.println("# Subject" + " " + subject.size());
		
		model.addAttribute("courseName", course.getName());
		model.addAttribute("subjects", subject);
		model.addAttribute("urlImage", course.getUrlImage());
		model.addAttribute("nameInternal", internalName);
		return "HTML/CourseInformation/subjects";
	}
	
	@RequestMapping("/course/{internalName}/skills")
	public String skills(Model model, @PathVariable String internalName) {

		course = courseRepository.findByInternalName(internalName);
		List<Skill> skill = course.getSkills();

		System.out.println("# skills" + " " + skill.size());

		model.addAttribute("skills", skill);
		model.addAttribute("urlImage", course.getUrlImage());
		model.addAttribute("nameInternal", internalName);

		return "HTML/CourseInformation/skills";
	}
	
	@RequestMapping("/course/{internalName}/{userName}/add")
	public ModelAndView addCourseToUser(Model model, 
			@PathVariable String internalName,
			@PathVariable String userName) {

		User user = userRepository.findByUsername(userName);

		System.out.println("# cursos inscritos" + " " + user.getInscribedCourses().size());
		for (int i=0; i<user.getInscribedCourses().size(); i++)
			System.out.println("# nombre" + " " + user.getInscribedCourses().get(i).getName());
		
		for (User u : userRepository.findAll()) {
			if (!u.getInscribedCourses().contains(course))
						
			System.out.println("course : " + course.getName() + " no lo tiene");
			
			user.getInscribedCourses().add(course);			
			user.setInscribedCourses(user.getInscribedCourses());
			System.out.println("# cursos inscritos" + " " + user.getInscribedCourses().size());
			
			user.setInscribedCourses(user.getInscribedCourses());
			
			userRepository.save(user);
		}
		
		return new ModelAndView("redirect:/profile/"+ user.getUsername());
	}
	
	

}
