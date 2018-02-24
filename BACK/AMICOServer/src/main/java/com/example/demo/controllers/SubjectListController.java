package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;
import com.example.demo.user_package.SessionUserComponent;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class SubjectListController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SessionUserComponent sessionUserComponent;

	@RequestMapping("/course-overview/{courseInternalName}")
	public String allCourses(Model model, @PathVariable String courseInternalName) {

		//User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");
		if (user != null) {
			Course actualCourse = null;
			for (Course course : user.getInscribedCourses()) {
				if (course.getInternalName().equals(courseInternalName)) {
					actualCourse = course;
				}
			}

			List<User> users = userRepository.findAll();
			List<User> teachers = new ArrayList<>();
			for (User u : users) {
				if (!u.isStudent()) {
					teachers.add(u);
				}
			}
			if (actualCourse != null) {
				model.addAttribute("subjects", actualCourse.getSubjects());
				model.addAttribute("courseName", actualCourse.getName());
				model.addAttribute("courseInternalName", actualCourse.getInternalName());
				model.addAttribute("userInternalName", user.getInternalName());
				model.addAttribute("allTeachers", teachers);

			}

			//model.AddAttribute("admin",user.isAdmin());
			// For development only
			model.addAttribute("admin", true);
		}

		return "HTML/StudentCourses/student-course-overview";
	}

	@RequestMapping(value = "/course/{courseInternalName}/update-subject/{subjetctInternalName}", method = RequestMethod.POST)
	public ModelAndView updateSubject(Model model, @PathVariable String courseInternalName,
			@PathVariable String subjetctInternalName,
			@RequestParam String subjectName, @RequestParam String description) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user.isAdmin())
		//{
			Course course = courseRepository.findByInternalName(courseInternalName);
			
			if (course != null && !subjetctInternalName.isEmpty()) {
				for (Subject subject : course.getSubjects()) {
					if (subject.getInternalName().equals(subjetctInternalName)) {
						if (!subjectName.isEmpty()) {
							subject.setName(subjectName);
							subject.setInternalName(subjectName.replaceAll(" ", "-"));
						}
						if (!description.isEmpty()) {
							subject.setDescription(description);
						}
						subjectRepository.save(subject);
					}
				}
			}
		//}
		return new ModelAndView("redirect:/course-overview/" + courseInternalName);
	}

	@RequestMapping(value = "/course/{courseInternalName}/delete-subject/{subjetctInternalName}/{userName}", method = RequestMethod.POST)
	public ModelAndView deleteSubject(Model model, @PathVariable String courseInternalName,
			@PathVariable String subjetctInternalName) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user.isAdmin())
		//{
			Course course = courseRepository.findByInternalName(courseInternalName);
	
			if (course != null) {
				Subject toDelete = null;
				for (Subject subject : course.getSubjects()) {
					if (subject.getInternalName().equals(subjetctInternalName)) {
						toDelete = subject;
					}
				}
	
				// subjectRepository.delete(toDelete);
				toDelete.setCourse(null);
				course.getSubjects().remove(toDelete);
	
				courseRepository.save(course);
	
			}
		//}
		return new ModelAndView("redirect:/course-overview/" + courseInternalName);
	}

	@RequestMapping(value = "/course/{courseInternalName}/create-subject", method = RequestMethod.POST)
	public ModelAndView createSubject(Model model, @PathVariable String courseInternalName, @RequestParam String subjectName) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user.isAdmin())
		//{
			
			Course course = courseRepository.findByInternalName(courseInternalName);
	
			if (course != null && !subjectName.isEmpty()) {
				Subject subject = new Subject(subjectName);
				course.getSubjects().add(subject);
				subject.setCourse(course);
	
				subjectRepository.save(subject);
				courseRepository.save(course);
	
			}
		//}
		return new ModelAndView("redirect:/course-overview/" + courseInternalName);
	}

	@RequestMapping(value = "/course/{courseInternalName}/updateTeachers-subject/{subjetctInternalName}", method = RequestMethod.POST)
	public ModelAndView ChangeTeachersFromSubject(Model model, @PathVariable String courseInternalName,
			@PathVariable String subjetctInternalName,
			@RequestParam String[] selectTeachers) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user.isAdmin())
		//{
			Course course = courseRepository.findByInternalName(courseInternalName);
	
			if (course != null && (selectTeachers.length != 0)) {
				Subject Toupdate = null;
				for (Subject subject : course.getSubjects()) {
					if (subject.getInternalName().equals(subjetctInternalName)) {
						for (User teacher : subject.getTeachers()) {
							teacher.getTeaching().remove(subject);
						}
						subject.getTeachers().removeAll(subject.getTeachers());
	
						List<User> newTeachers = new ArrayList<>();
						int i = 0;
						for (String s : selectTeachers) {
							newTeachers.add(userRepository.findByUsername(s));
							newTeachers.get(i).getTeaching().add(subject);
						}
						subject.getTeachers().addAll(newTeachers);
						subjectRepository.save(subject);
	
					}
				}
			// subjectRepository.delete(toDelete);
			// toUpdate.setCourse(null);
			}
		//}
		return new ModelAndView("redirect:/course-overview/" + courseInternalName);
	}

}
