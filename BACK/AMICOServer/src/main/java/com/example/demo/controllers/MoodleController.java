package com.example.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.studyItem_package.StudyItem;
import com.example.demo.studyItem_package.StudyItemRepository;
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
	@Autowired
	private StudyItemRepository studyItemRepository;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	@RequestMapping("/moodle/{courseInternalName}/{subjectInternalName}")
	public String allCourses(Model model, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		/*
		 * This is for when we have the login system.s User user =
		 * sessionUserComponent.getLoggedUser(); Course course = null;
		 * 
		 * for (Course courseAct : user.getInscribedCourses()) { if
		 * (courseAct.getInternalName().equals(courseInternalName)) { course =
		 * courseAct; } }
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

		// model.addAttribute("isTeacher", !user.isStudent());
		model.addAttribute("isTeacher", true);

		/* Info about course and subject */
		model.addAttribute("courseInternalName", course.getInternalName());
		model.addAttribute("courseName", course.getName());
		model.addAttribute("subjectInternalName", subject.getInternalName());
		model.addAttribute("subjectName", subject.getName());

		return "HTML/Moodle/student-subject";
	}

	@RequestMapping(value = "/moodle/upload/studyItem/{courseInternalName}/{subjectInternalName}/{module}", method = RequestMethod.POST)
	private ModelAndView uploadStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module, @RequestParam String itemName,
			@RequestParam String itemType, @RequestParam("itemFile") MultipartFile file) {

		// User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");

		// if (user.getInscribedCourses())

		if (!user.isStudent() && !itemName.isEmpty() && (module != null)) {

			Course course = null;
			for (Course courseAct : user.getInscribedCourses()) {
				if (courseAct.getInternalName().equals(courseInternalName)) {
					course = courseAct;
				}
			}

			if (course != null) {
				Subject subject = null;
				for (Subject subjectAct : course.getSubjects()) {
					if (subjectAct.getInternalName().equals(subjectInternalName)) {
						subject = subjectAct;
					}
				}
				if (subject != null) {
					if (subject.getTeachers().contains(user)) {
						/* If the user is a teacher of the subject can upload the file */

						/* File uploading control. If a profile image exists, it is overwritten */
						/* If there is not file the imageName wont change */
						if (!file.isEmpty()) {
							try {
								Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
										"files/documents/" + course.getCourseID() + "/" + subject.getSubjectID() + "/");
								if (!Files.exists(FILES_FOLDER)) {
									Files.createDirectories(FILES_FOLDER);
								}
								String[] fileOriginal = file.getOriginalFilename().split("[.]");
								System.err.println(fileOriginal.length);
								String extension = fileOriginal[fileOriginal.length - 1];
								String type = extension;
								if (!itemType.isEmpty()) {
									type = itemType;
								}
								StudyItem studyItem = new StudyItem(type, itemName, file.getOriginalFilename());

								studyItemRepository.save(studyItem);

								String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
								studyItem.setFileName(fileName);
								studyItem.setModule(module);

								studyItem.setSubject(subject);
								subject.getStudyItemsList().add(studyItem);
								File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
								file.transferTo(uploadedFile);

								studyItemRepository.save(studyItem);
								subjectRepository.save(subject);

							} catch (IOException e) {
								System.out.println(e.getMessage());
							}
						}

					}
				}
			}

		}

		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

}
