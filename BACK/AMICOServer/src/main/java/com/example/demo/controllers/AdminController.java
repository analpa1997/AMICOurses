package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.example.demo.skill.Skill;
import com.example.demo.skill.SkillRepository;
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
	private SkillRepository skillRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;

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

	@RequestMapping(value = "/admin/modify/course/{courseInternalName}", method = RequestMethod.POST)
	private ModelAndView modifyCourse(@PathVariable String courseInternalName, @RequestParam String newName,
			@RequestParam String newLanguage, @RequestParam String newType, @RequestParam String newDescription,
			@RequestParam(required = false) String btnSave, @RequestParam(required = false) String btnDelete) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {
			Course course = courseRepository.findByInternalName(courseInternalName);
			if (course != null) {
				/* Save */
				if (btnSave != null) {
					if (!newName.isEmpty()) {
						course.setName(newName);
					}
					if (!newType.isEmpty()) {
						course.setType(newType);
					}
					if (!newLanguage.isEmpty()) {
						course.setCourseLanguage(newLanguage);
					}
					if (!newDescription.isEmpty()) {
						course.setCourseDescription(newDescription);
					}
					courseRepository.save(course);

				} else {
					/* Delete */
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

			}

		}

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/delete/{userInternalName}")
	private ModelAndView modifyCourse(@PathVariable String userInternalName) {
		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {

		}

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/create/teacher", method = RequestMethod.POST)
	private ModelAndView createTeacher(@RequestParam String newName, @RequestParam String newSecondName,
			@RequestParam String newUsername, @RequestParam String newUserMail, @RequestParam String newPassword) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {

			checkUser(newUsername, newPassword, newPassword, newUserMail, true);

		}

		return new ModelAndView("redirect:/admin/");
	}

	@RequestMapping(value = "/admin/create/course", method = RequestMethod.POST)
	private ModelAndView createCourse(@RequestParam String newName, @RequestParam String newLanguage,
			@RequestParam String newType, @RequestParam String newSkill1, @RequestParam String newSkill2,
			@RequestParam String newSkill3, @RequestParam Date startDate, @RequestParam Date endDate,
			@RequestParam String newDescription, @RequestParam("courseImage") MultipartFile file) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {
			Course course = courseRepository.findByName(newName);

			if (course == null) {
				if (!newName.isEmpty() && !newLanguage.isEmpty() && !newDescription.isEmpty() && !newType.isEmpty()
						&& !newSkill1.isEmpty()) {
					course = new Course(newName, newLanguage, newDescription, newType, "");
					course.setStartDate(startDate);
					course.setEndDate(endDate);
					course.getInscribedUsers().add(user);
					user.getInscribedCourses().add(course);

					courseRepository.save(course);
					userRepository.save(user);
					course = courseRepository.findByName(course.getName());

					Skill skill1 = new Skill(newSkill1);
					skill1.setCourse(course);
					skillRepository.save(skill1);

					if (!newSkill2.isEmpty()) {
						Skill skill2 = new Skill(newSkill2);
						skill2.setCourse(course);
						skillRepository.save(skill2);
					}

					if (!newSkill3.isEmpty()) {
						Skill skill3 = new Skill(newSkill3);
						skill3.setCourse(course);
						skillRepository.save(skill3);
					}

					if (!file.isEmpty()) {
						try {
							Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
									"files/image/courses/" + course.getCourseID() + "/");
							if (!Files.exists(FILES_FOLDER)) {
								Files.createDirectories(FILES_FOLDER);
							}

							String fileName = "course-" + course.getCourseID() + ".jpg";

							File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
							file.transferTo(uploadedFile);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}

		}

		return new ModelAndView("redirect:/admin/");
	}

}