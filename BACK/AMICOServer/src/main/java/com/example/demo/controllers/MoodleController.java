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
import com.example.demo.studyItem_package.StudyItem;
import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;
import com.example.demo.user_package.SessionUserComponent;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Controller
public class MoodleController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	
	/*@Autowired
	private SessionUserComponent sessionUserComponent;*/

	@RequestMapping("/moodle/{courseInternalName}/{subjectInternalName}")
	public String allCourses(Model model, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		/* This is for when we have the login system.s
		User user = sessionUserComponent.getLoggedUser();
		Course course = null;
		
		for (Course courseAct : user.getInscribedCourses()) {
			if (courseAct.getInternalName().equals(courseInternalName)) {
				course = courseAct;
			}
		}
		*/
		/* this line wont be useful */
		Course course = courseRepository.findByInternalName(courseInternalName);
		
		Subject subject = null;
		for (Subject subjectAct : course.getSubjects()) {
			if (subjectAct.getInternalName().equals(subjectInternalName)) {
				subject = subjectAct;
			}
		}

		if (subject != null) {

			/* Get all the studyItems from the subject */
			/*
			 * They will consists in n (number of modules a subject has) Lists in order to
			 * pass them to moustache
			 */
			List<List<StudyItem>> allStudyItems = new ArrayList<>();

			for (int i = 0; i < subject.getNumberModules(); i++) {
				allStudyItems.add(new ArrayList<>());
			}

			int module;
			List<StudyItem> moduleNStudyItems;
			for (StudyItem studyItem : subject.getStudyItemsList()) {
				/* Get the actual module */
				module = studyItem.getModule() - 1;
				moduleNStudyItems = allStudyItems.get(module);
				/* If it is the first item on this module, the list is null */
				if (moduleNStudyItems == null) {
					moduleNStudyItems = new ArrayList<>();
				}
				moduleNStudyItems.add(studyItem);
			}

			model.addAttribute("allStudyItems", allStudyItems);

		}
		
		//model.addAttribute("isTeacher", !user.isStudent());
		model.addAttribute("isTeacher", true);
		return "HTML/Moodle/student-subject";
	}
}


