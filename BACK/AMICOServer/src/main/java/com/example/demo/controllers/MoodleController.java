package com.example.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
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
		 */
		User user = userRepository.findByInternalName("amicoteacher");

		Course course = null;
		for (Course courseAct : user.getInscribedCourses()) {
			if (courseAct.getInternalName().equals(courseInternalName)) {
				course = courseAct;
			}
		}

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

		model.addAttribute("isTeacher", !user.isStudent());

		/* Info about course and subject */
		model.addAttribute("courseInternalName", course.getInternalName());
		model.addAttribute("courseName", course.getName());
		model.addAttribute("subjectInternalName", subject.getInternalName());
		model.addAttribute("subjectName", subject.getName());

		return "HTML/Moodle/student-subject";
	}

	@RequestMapping("/moodle/module-{option}/{courseInternalName}/{subjectInternalName}")
	private ModelAndView moduleAction(@PathVariable String option, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @RequestParam(required = false) Integer module) {

		// User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");

		if (!user.isStudent() && !option.isEmpty() && (option.equals("add") || option.equals("delete"))) {

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
						if (option.equals("add")) {
							subject.addModule();
							subjectRepository.save(subject);
						} else {
							if (option.equals("delete") && (module != null)) {
								/* Get the studyItems of the module */
								List<StudyItem> studyItemsToRemove = new ArrayList<>();
								for (StudyItem studyItemAct : subject.getStudyItemsList()) {
									if (studyItemAct.getModule() == module) {
										studyItemsToRemove.add(studyItemAct);
										studyItemAct.setSubject(null);
									} else {
										if (studyItemAct.getModule() > module) {
											studyItemAct.setModule(studyItemAct.getModule() - 1);
										}
									}
								}
								/* Now remove the studyItems from the subject */
								subject.getStudyItemsList().removeAll(studyItemsToRemove);
								studyItemRepository.delete(studyItemsToRemove);
								subject.deleteModule();

								subjectRepository.save(subject);
							}
						}

					}
				}
			}
		}

		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

	@RequestMapping(value = "/moodle/upload/studyItem/{courseInternalName}/{subjectInternalName}/{module}", method = RequestMethod.POST)
	private ModelAndView uploadStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module, @RequestParam String itemName,
			@RequestParam String itemType, @RequestParam("itemFile") MultipartFile file) {

		// User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");

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
								Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/documents/"
										+ course.getCourseID() + "/" + subject.getSubjectID() + "/studyItems/");
								if (!Files.exists(FILES_FOLDER)) {
									Files.createDirectories(FILES_FOLDER);
								}
								String[] fileOriginal = file.getOriginalFilename().split("[.]");
								String extension = fileOriginal[fileOriginal.length - 1];
								String type = extension;
								if (!itemType.isEmpty()) {
									type = itemType;
								}
								StudyItem studyItem = new StudyItem(type, itemName, module, file.getOriginalFilename());

								studyItemRepository.save(studyItem);

								String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
								studyItem.setExtension(extension);
								studyItem.setFileName(fileName);

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

	@RequestMapping("/moodle/download/studyItem/{courseInternalName}/{subjectInternalName}/{studyItemID}")
	private void getStudyItemFile(@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable Long studyItemID, HttpServletResponse res) throws FileNotFoundException, IOException {

		// User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");

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
				if (subject.getUsers().contains(user)) {
					StudyItem studyItem = null;
					for (StudyItem studyItemAct : subject.getStudyItemsList()) {
						if (studyItemAct.getStudyItemID() == studyItemID.longValue()) {
							studyItem = studyItemAct;
						}
					}

					if (studyItem != null) {

						Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/documents/"
								+ course.getCourseID() + "/" + subject.getSubjectID() + "/studyItems/");
						String extension = studyItem.getExtension();
						if (extension == null) {
							String[] fileOriginal = studyItem.getOriginalName().split("[.]");
							extension = fileOriginal[fileOriginal.length - 1];
						}
						Path filePath = FILES_FOLDER
								.resolve("studyItem-" + studyItem.getStudyItemID() + "." + extension);

						res.addHeader("Content-Disposition", "attachment; filename = " + studyItem.getOriginalName());
						res.setContentType("application/octet-stream");
						res.setContentLength((int) filePath.toFile().length());
						FileCopyUtils.copy(Files.newInputStream(filePath), res.getOutputStream());
					}
				}
			}
		}

	}

	@RequestMapping(value = "/moodle/studyItem/modify/{courseInternalName}/{subjectInternalName}/{studyItemID}", method = RequestMethod.POST)
	private ModelAndView modifyStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID, @RequestParam Integer newPosition,
			@RequestParam String newName, @RequestParam String newType, @RequestParam(required = false) String btnSave,
			@RequestParam(required = false) String btnDelete) {

		// User user = sessionUserComponent.getLoggedUser();
		User user = userRepository.findByInternalName("amicoteacher");

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
				if (subject.getUsers().contains(user)) {
					StudyItem studyItem = null;
					for (StudyItem studyItemAct : subject.getStudyItemsList()) {
						if (studyItemAct.getStudyItemID() == studyItemID.longValue()) {
							studyItem = studyItemAct;
						}
					}

					if (studyItem != null) {
						
						if (btnSave != null) {
							/* Change the studyItem*/
							if (!newName.isEmpty()) {
								studyItem.setName(newName);
							}
							if (!newType.isEmpty()) {
								studyItem.setType(newType);
							}
							studyItemRepository.save(studyItem);
							
							if (newPosition != null) {
								int startModule = -1;
								int sizeModule = 0;
								int i = 0;
								int antPos = 0;
								int module = studyItem.getModule();
								for (StudyItem studyItemAct : subject.getStudyItemsList()) {
									if (studyItemAct.getModule() == module) {
										if (startModule < 0) {
											startModule = i;
										}
										if (studyItemAct.getStudyItemID() == studyItem.getStudyItemID()) {
											antPos = i;
										}
										sizeModule++;
									}
									i++;
								}
								if ((antPos + startModule) != (newPosition-1 + startModule) && (sizeModule < subject.getStudyItemsList().size())) {
									subject.getStudyItemsList().add((newPosition-1+startModule), studyItem);;
									subject.getStudyItemsList().remove(startModule+antPos);
								}
								subjectRepository.save(subject);
							}
							
						} else {
							/* Delete the studyItem*/
							if (btnDelete != null) {
								studyItem.setSubject(null);
								subject.getStudyItemsList().remove(studyItem);
								studyItemRepository.delete(studyItem);
								subjectRepository.save(subject);
							}
						}

					}
				}
			}
		}

		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

}
