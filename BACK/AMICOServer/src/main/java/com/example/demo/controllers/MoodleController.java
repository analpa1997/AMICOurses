package com.example.demo.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.studyItem.StudyItemService;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import com.example.demo.subject.SubjectService;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

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
	private PracticesRepository practicesRepository;

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudyItemService studyItemService;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	@RequestMapping("/moodle/{courseInternalName}/{subjectInternalName}")
	public String allCourses(Model model, Pageable pages, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @RequestParam(value = "page", defaultValue = "0") int page) {
		User user = sessionUserComponent.getLoggedUser();

		Course course = null;
		course = courseRepository.findByInternalName(courseInternalName);

		if (course.getInscribedUsers().contains(user)) {

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

				List<StudyItem> studyItemPractices = new ArrayList<>();
				for (int i = 1; i <= subject.getNumberModules(); i++) {
					allStudyItems.add(subjectService.getStudyItems(subject, i, new PageRequest(page, 10)).getContent());
				}

				studyItemPractices = subjectService.getPractices(subject);

				model.addAttribute("studyItemPractices", studyItemPractices);
				model.addAttribute("allStudyItems", allStudyItems);

				List<List<Object>> studentPractices = new ArrayList<>();
				/*
				 * To retrieve only the student practices (one practice per studyItemPractice)
				 */

				int i = 0;
				if (user.isStudent()) {

					for (StudyItem studyItemPrac : studyItemPractices) {
						studentPractices.add(new ArrayList<>());

						Practices practice = null;
						for (Practices practiceAct : studyItemPrac.getPractices()) {
							if (practiceAct.getOwner().getUserID() == user.getUserID()) {
								practice = practiceAct;
							}
						}
						if (practice == null) {
							practice = new Practices("Not Presented", "Not Presented");
							practice.setStudyItem(studyItemPrac);
							practice.setOwner(user);
							practicesRepository.save(practice);
							practice = practicesRepository.getOne(practicesRepository.count());
							studyItemPrac.getPractices().add(practice);
							studyItemRepository.save(studyItemPrac);
						}
						studentPractices.get(i).add(studyItemPrac);
						studentPractices.get(i).add(practice);

						i++;
					}
					model.addAttribute("studentPractices", studentPractices);
				}

				/* Users to show in the teachers/students tab */
				List<User> usersToShowList = new ArrayList<>();
				if (user.isStudent()) {
					usersToShowList.addAll(subject.getTeachers());
				}
				if (!user.isStudent()) {
					usersToShowList.addAll(subject.getUsers());
				}
				usersToShowList.remove(user);
				model.addAttribute("usersToShowList", usersToShowList);
			}

			model.addAttribute("isTeacher", !user.isStudent());

			/* Info about the user, course and subject */
			model.addAttribute("courseInternalName", course.getInternalName());
			model.addAttribute("courseName", course.getName());
			model.addAttribute("subjectInternalName", subject.getInternalName());
			model.addAttribute("subjectName", subject.getName());
			model.addAttribute("userInternalName", user.getInternalName());

			return "HTML/Moodle/student-subject";
		} else {
			return "/error/";
		}
	}

	@RequestMapping("/moodle/module-{option}/{courseInternalName}/{subjectInternalName}")
	private ModelAndView moduleAction(@PathVariable String option, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @RequestParam(required = false) Integer module) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent() && !option.isEmpty()
				&& (option.equals("add") || option.equals("delete"))) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					if (option.equals("add")) {
						subjectService.addModule(subject);
					} else {
						if (option.equals("delete") && (module != null)) {
							subjectService.deleteModule(subject, module);
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

		User user = sessionUserComponent.getLoggedUser();

		if (!user.isStudent() && !itemName.isEmpty() && (module != null)) {

			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getTeachers().contains(user)) {
					/* If the user is a teacher of the subject can upload the file */

					/* File uploading control. If the file exists, it is overwritten */

					if (!file.isEmpty()) {
						try {
							studyItemService.createStudyItem(file, subject, module, itemType, itemName);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}

				}
			}
		}

		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

	@RequestMapping({ "/moodle/download/studyItem/{courseInternalName}/{subjectInternalName}/{studyItemID}",
			"/moodle/download/studyItem/{courseInternalName}/{subjectInternalName}/{studyItemID}/{practiceID}" })
	private void getStudyItemFile(@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable Long studyItemID, @PathVariable Optional<Long> practiceID, HttpServletResponse res)
			throws FileNotFoundException, IOException {

		User user = sessionUserComponent.getLoggedUser();

		Course course = null;
		course = courseRepository.findByInternalName(courseInternalName);

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
						/* An studyItem */
						if (!practiceID.isPresent()) {

							res.addHeader("Content-Disposition",
									"attachment; filename = " + studyItem.getOriginalName());
							Path filePath = studyItemService.getStudyItemFile(course.getCourseID(),
									subject.getSubjectID(), studyItemID);
							res.setContentType("application/octet-stream");
							res.setContentLength((int) filePath.toFile().length());

							FileCopyUtils.copy(Files.newInputStream(filePath), res.getOutputStream());

							/* A practice */
						} else {
							Practices practice = null;
							for (Practices practiceAct : studyItem.getPractices()) {
								if (practiceAct.getPracticeID() == practiceID.get()) {
									practice = practiceAct;
								}
							}

							if (practice != null
									&& (!user.isStudent() || practice.getOwner().getUserID() == user.getUserID())) {
								Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
										"files/documents/" + course.getCourseID() + "/" + subject.getSubjectID()
												+ "/studyItems/practices/");

								String[] fileOriginal = studyItem.getOriginalName().split("[.]");
								String extension = fileOriginal[fileOriginal.length - 1];

								Path filePath = FILES_FOLDER
										.resolve("practice-" + practice.getPracticeID() + "." + extension);

								res.addHeader("Content-Disposition",
										"attachment; filename = " + practice.getOriginalName());
								res.setContentType("application/octet-stream");
								res.setContentLength((int) filePath.toFile().length());
								FileCopyUtils.copy(Files.newInputStream(filePath), res.getOutputStream());
							}
						}
					}
				}
			}
		}

	}

	@RequestMapping(value = "/moodle/studyItem/modify/{courseInternalName}/{subjectInternalName}/{studyItemID}", method = RequestMethod.POST)
	private ModelAndView modifyStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID, @RequestParam String newName,
			@RequestParam String newType, @RequestParam(required = false) String btnSave,
			@RequestParam(required = false) String btnDelete) throws IOException {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);

			if (subject != null) {
				StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);

				if (studyItem != null) {

					if (btnSave != null) {
						/* Change the studyItem */
						studyItemService.modifyStudyItem(studyItem, new StudyItem(newType, newName, null, null));

					} else {
						/* Delete the studyItem */
						if (btnDelete != null) {
							if (!studyItem.isPractice()) {
								studyItem.setSubject(null);
								subject.getStudyItemsList().remove(studyItem);
								studyItemRepository.delete(studyItem);
								// subjectRepository.save(subject);
							} else {
								List<Practices> toRemove = new ArrayList<>();
								for (Practices pract : studyItem.getPractices()) {
									pract.setStudyItem(null);
									pract.setOwner(null);
									toRemove.add(pract);
								}
								studyItem.getPractices().removeAll(toRemove);
								studyItemRepository.delete(studyItem);
								practicesRepository.delete(toRemove);

							}
						}
					}

				}

			}
		}

		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

	@RequestMapping(value = "/moodle/practice/update-calification/{courseInternalName}/{subjectInternalName}/{studyItemID}/{practiceID}", method = RequestMethod.POST)
	private ModelAndView updateCalificationPractice(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID, @PathVariable Long practiceID,
			@RequestParam Double newCalification) {

		User user = sessionUserComponent.getLoggedUser();
		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				if (subject.getUsers().contains(user)) {
					StudyItem studyItem = null;
					for (StudyItem studyItemAct : subject.getStudyItemsList()) {
						if (studyItemAct.getStudyItemID() == studyItemID.longValue()) {
							studyItem = studyItemAct;
						}
					}

					if (studyItem != null) {
						Practices practice = null;
						for (Practices practiceAct : studyItem.getPractices()) {
							if (practiceAct.getPracticeID() == practiceID) {
								practice = practiceAct;
							}
						}

						if (practice != null && (!user.isStudent())) {
							practice.setCalification(newCalification);
							practice.setCorrected(true);
							practicesRepository.save(practice);
						}
					}
				}
			}
		}
		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

	@RequestMapping(value = "/moodle/practice/modify/{courseInternalName}/{subjectInternalName}/{studyItemID}/{practiceID}", method = RequestMethod.POST)
	private ModelAndView modifyPractice(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID, @PathVariable Long practiceID,
			@RequestParam String newName, @RequestParam("itemFile") MultipartFile file) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && user.isStudent() && !newName.isEmpty()) {

			Course course = null;
			course = courseRepository.findByInternalName(courseInternalName);

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
							Practices practice = null;
							for (Practices practiceAct : studyItem.getPractices()) {
								if (practiceAct.getPracticeID() == practiceID) {
									practice = practiceAct;
								}
							}

							if (practice != null)
								if (!newName.isEmpty()) {
									practice.setPracticeName(newName);
									practice.setPresented(true);
									practicesRepository.save(practice);
								}

							if (!file.isEmpty() && (practice != null)) {
								try {
									Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
											"files/documents/" + course.getCourseID() + "/" + subject.getSubjectID()
													+ "/studyItems/practices/");
									if (!Files.exists(FILES_FOLDER)) {
										Files.createDirectories(FILES_FOLDER);
									}

									practice.setOriginalName(file.getOriginalFilename());
									practicesRepository.save(practice);

									String[] fileOriginal = studyItem.getOriginalName().split("[.]");
									String extension = fileOriginal[fileOriginal.length - 1];

									String fileName = "practice-" + practice.getPracticeID() + "." + extension;

									File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
									file.transferTo(uploadedFile);

								} catch (IOException e) {
									System.out.println(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
		return new ModelAndView("redirect:/moodle/" + courseInternalName + "/" + subjectInternalName);
	}

}
