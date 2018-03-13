package com.example.demo.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.course.CourseService;
import com.example.demo.skill.Skill;
import com.example.demo.subject.Subject;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;

@Controller
public class AdminController extends UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private SessionUserComponent sessionUserComponent;

	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "/admin/create/course", method = RequestMethod.POST)
	private ModelAndView createCourse(@RequestParam String newName, @RequestParam String newLanguage,
			@RequestParam String newType, @RequestParam String newSkill1, @RequestParam String newSkill2,
			@RequestParam String newSkill3, @RequestParam Date startDate, @RequestParam Date endDate,
			@RequestParam String newDescription, @RequestParam("courseImage") MultipartFile file) {

		courseService.createCourse(newName, newLanguage, newType, newSkill1, newSkill2, newSkill3, startDate, endDate,
				newDescription, file);

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/create/teacher", method = RequestMethod.POST)
	private ModelAndView createTeacher(@RequestParam String newName, @RequestParam String newSecondName,
			@RequestParam String newUsername, @RequestParam String newUserMail, @RequestParam String newPassword) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin())
			checkUser(newUsername, newPassword, newPassword, newUserMail, true);

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/delete/{userInternalName}")
	private ModelAndView modifyCourse(@PathVariable String userInternalName) {
		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {

		}

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/modify/course/{courseInternalName}", method = RequestMethod.POST)
	private ModelAndView modifyCourse(@PathVariable String courseInternalName, @RequestParam String newName,
			@RequestParam String newLanguage, @RequestParam String newType, @RequestParam String newDescription,
			@RequestParam(required = false) String btnSave, @RequestParam(required = false) String btnDelete) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {
			Course course = courseRepository.findByInternalName(courseInternalName);
			if (course != null)
				/* Save */
				if (btnSave != null) {
					if (!newName.isEmpty())
						course.setName(newName);
					if (!newType.isEmpty())
						course.setType(newType);
					if (!newLanguage.isEmpty())
						course.setCourseLanguage(newLanguage);
					if (!newDescription.isEmpty())
						course.setCourseDescription(newDescription);
					courseRepository.save(course);

				} else /* Delete */
				if (btnDelete != null) {
					List<Subject> toRemoveS = new ArrayList<>();
					for (Subject subjectAct : course.getSubjects()) {
						subjectAct.setCourse(null);
						toRemoveS.add(subjectAct);
					}
					course.getSubjects().removeAll(toRemoveS);

					List<User> toRemoveU = new ArrayList<>();
					for (User userAct : course.getInscribedUsers()) {
						userAct.getInscribedCourses().remove(course);
						toRemoveU.add(userAct);
					}
					course.getInscribedUsers().removeAll(toRemoveU);

					List<Skill> toRemoveSkill = new ArrayList<>();
					for (Skill skillAct : course.getSkills()) {
						skillAct.setCourse(null);
						toRemoveSkill.add(skillAct);
					}
					course.getSkills().removeAll(toRemoveSkill);
					courseRepository.delete(course);
				}

		}

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping("/admin")
	public String viewProfile(Model model) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {

			List<Course> allCourses = courseRepository.findAll();
			List<User> teachers = userRepository.findByIsStudent(false);

			model.addAttribute("allCourses", allCourses);
			model.addAttribute("teachers", teachers);
		}

		return "HTML/Admin/admin_page";
	}

}