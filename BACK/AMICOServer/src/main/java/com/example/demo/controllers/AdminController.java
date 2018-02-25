package com.example.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.skill_package.Skill;
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.SessionUserComponent;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

@Controller
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	@RequestMapping("/admin")
	public String viewProfile(Model model) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user != null && user.getRole() == "ROLE_ADMIN") {
			
			List<Course> allCourses = courseRepository.findAll();
			List<User> teachers = userRepository.findByIsStudent(false);
			
			model.addAttribute("allCourses", allCourses);
			model.addAttribute("teachers", teachers);
		//}
		
		
		return "HTML/Admin/admin_page";
	}
	
	@RequestMapping (value = "/admin/modify/course/{courseInternalName}", method = RequestMethod.POST)
	private ModelAndView modifyCourse (@PathVariable String courseInternalName, @RequestParam String newName, @RequestParam String newLanguage, 
			@RequestParam String newType, @RequestParam String newDescription, @RequestParam(required = false) String btnSave,
			@RequestParam(required = false) String btnDelete ) {
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user != null && user.getRole() == "ROLE_ADMIN") {
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
					List<Subject> toRemoveS = new ArrayList<> ();
					for (Subject subjectAct : course.getSubjects()) {
						subjectAct.setCourse(null);
						toRemoveS.add(subjectAct);
					}
					course.getSubjects().removeAll(toRemoveS);
					
					List<User> toRemoveU = new ArrayList<> ();
					for (User userAct : course.getInscribedUsers()) {
						userAct.getInscribedCourses().remove(course);
						toRemoveU.add(userAct);
					}
					course.getInscribedUsers().removeAll(toRemoveU);
					
					List <Skill> toRemoveSkill = new ArrayList<> ();
					for (Skill skillAct : course.getSkills()) {
						skillAct.setCourse(null);
						toRemoveSkill.add(skillAct);
					}
					course.getSkills().removeAll(toRemoveSkill);
					courseRepository.delete(course);
				}
			}
			
		}
		
		
		
	//}
		
		return new ModelAndView("redirect:/admin/");
	}
	
	@RequestMapping (value = "/admin/delete/{userInternalName}")
	private ModelAndView modifyCourse (@PathVariable String userInternalName) {
		//User user = sessionUserComponent.getLoggedUser();
		//if (user != null && user.getRole() == "ROLE_ADMIN") {
		User userToRemove = userRepository.findByInternalName(userInternalName);
		
		List <Course> coursesToRemove = new ArrayList <> ();
		for (Course courseAct : userToRemove.getInscribedCourses()) {
			List <Subject> subjectsToRemove = new ArrayList<> ();
			for (Subject subjectAct : courseAct.getSubjects()) {
				subjectAct.getTeachers().remove(userToRemove);
			}
			userToRemove.getTeaching().removeAll(subjectsToRemove);
			courseAct.getInscribedUsers().remove(userToRemove);
			coursesToRemove.add(courseAct);
		}
		
		userToRemove.getInscribedCourses().removeAll(coursesToRemove);
		userToRemove.getCompletedCourses().removeAll(coursesToRemove);
		userRepository.delete(userToRemove);
		
		//}
		
		return new ModelAndView("redirect:/admin/");
	}
	
	@RequestMapping(value = "/admin/create/teacher" , method = RequestMethod.POST)
	private ModelAndView createTeacher (@RequestParam String newName, @RequestParam String newSecondName, @RequestParam String newUsername, @RequestParam String newUserMail, @RequestParam String newPassword) {  
		
		//User user = sessionUserComponent.getLoggedUser();
		//if (user != null && user.getRole() == "ROLE_ADMIN") {
		User user = userRepository.findByUsername(newUsername);
		if (user == null && !newUsername.isEmpty() && !newUserMail.isEmpty() && !newPassword.isEmpty()) {
			user = new User (newUsername, newPassword, newUserMail, false);
			user.setUserFirstName(newName);
			user.setUserLastName(newSecondName);
			userRepository.save(user);
		}
		
		//}
	
		return new ModelAndView("redirect:/admin/");
	}

	
	
}