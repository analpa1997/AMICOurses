package com.example.demo.restControllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.practices.Practices;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.studyItem.StudyItemService;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import com.example.demo.subject.SubjectService;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
public class MoodleRestController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudyItemService studyItemService;

	@Autowired
	private SessionUserComponent sessionUserComponent;

	/* MODULES */
	/* POST */
	/* Creates a new module within a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module", method = RequestMethod.POST)
	public ResponseEntity<Subject> addModule(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				subjectService.addModule(subject);
				return new ResponseEntity<>(subject, HttpStatus.OK);
			}
		}
		/* If program reaches here is that there has been an error */
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* DELETE */
	/* Deletes new module within a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module/{module}", method = RequestMethod.DELETE)
	public ResponseEntity<Subject> deleteModule(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Integer module) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null && !user.isStudent()) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				subjectService.deleteModule(subject, module);
				return new ResponseEntity<>(subject, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* STUDY ITEMS */

	/* GET */
	/* Retrieves all the studyItems (not practices enouncements) from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/all", method = RequestMethod.GET)
	public ResponseEntity<Page<StudyItem>> getAllStudyItems(Pageable pages, @PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @RequestParam(value = "page", defaultValue = "0") int page) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				Page<StudyItem> studyItems = subjectService.getStudyItems(subject, new PageRequest(page, 10));
				return new ResponseEntity<>(studyItems, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Retrieves all the studyItems from a module from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.GET)
	public ResponseEntity<Page<StudyItem>> getStudyItemsFromModule(Pageable pages,
			@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable Integer module, @RequestParam(value = "page", defaultValue = "0") int page) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				Page<StudyItem> studyItems = subjectService.getStudyItems(subject, module, new PageRequest(page, 10));
				return new ResponseEntity<>(studyItems, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* Retrieves only one studyItem from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/one/{studyItemID}", method = RequestMethod.GET)
	public ResponseEntity<StudyItem> getOneStudyItem(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName, @PathVariable Long studyItemID) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				StudyItem studyItem = subjectService.getStudyItem(subject, studyItemID);

				if (studyItem != null) {
					return new ResponseEntity<>(studyItem, HttpStatus.OK);
				}

			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* fileType can be or studyItem or practice */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/{fileType}/file/{studyItemID}", method = RequestMethod.GET)
	public void getStudyItemFile(@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
			@PathVariable String fileType, @PathVariable Long studyItemID, HttpServletResponse response)
			throws IOException {

		User user = sessionUserComponent.getLoggedUser();
		
		if (user != null && (fileType != null && !fileType.isEmpty())) {
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null && studyItemID != null) {
				StudyItem studyItem = studyItemRepository.findOne(studyItemID);

				/*
				 * If the filetype and the actual type of the studyItem/practiceEnouncement
				 * match
				 */
				boolean isValidRequest = ((fileType.equals("studyItem") && !studyItem.isPractice())
						|| (fileType.equals("practiceE") && studyItem.isPractice()));
				if (studyItem != null && isValidRequest) {
					Path pathFile = studyItemService.getStudyItemFile(subject.getCourse().getCourseID(),
							subject.getSubjectID(), studyItemID);
					try {
						response.addHeader("Content-Disposition",
								"attachment; filename = " + studyItem.getOriginalName());
						response.setContentType("application/octet-stream");
						response.setContentLength((int) pathFile.toFile().length());
						IOUtils.copy(Files.newInputStream(pathFile), response.getOutputStream());

					} catch (IOException e) {
						e.printStackTrace();
						response.sendError(404);
					}
				}
			}
		}
	}

	/* POST */
	/* Creates a studyItem within a subject and a module */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.POST)
	public ResponseEntity<StudyItem> createStudyItem(@PathVariable String courseInternalName,
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
							StudyItem response = studyItemService.createStudyItem(file, subject, module, itemType,
									itemName);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}

				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/* UPDATE */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.PUT)
	public ResponseEntity<StudyItem> updateStudyItem(@PathVariable String courseInternalName,
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
							StudyItem response = studyItemService.createStudyItem(file, subject, module, itemType,
									itemName);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}

				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	

	/* DELETE */

	/* PRACTICES */

	/* GET */

	/* POST */

	/* UPDATE */

	/* DELETE */

	/* CONSULT MARKS */
	/*
	 * TO DO: refractor all this autentication and checkings into a service/function
	 */
	@RequestMapping(value = "/api/moodle/practicesMarks/{courseInternalName}/{subjectInternalName}/practices/", method = RequestMethod.GET)
	public ResponseEntity<List<List<Object>>> getMarks(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			/* Return the marks of every practice */
			Subject subject = subjectService.checkForSubject(user, courseInternalName, subjectInternalName);
			if (subject != null) {
				List<StudyItem> studyItemsPract = new ArrayList<>();
				for (StudyItem studyItemAct : subject.getStudyItemsList()) {
					if (studyItemAct.isPractice()) {
						studyItemsPract.add(studyItemAct);
					}
				}

				List<List<Object>> listPractices = new ArrayList<>();
				int i = 0;
				for (StudyItem studyItemAct : studyItemsPract) {
					listPractices.add(new ArrayList<>());
					listPractices.get(i).add(studyItemAct.getName());
					double mean = 0;
					int n = 0;
					for (Practices practiceAct : studyItemAct.getPractices()) {
						if (user.isStudent()) {
							if (practiceAct.getOwner().getUserID() == user.getUserID()) {
								listPractices.get(i).add(practiceAct.getCalification());
							}
						} else {
							if (practiceAct.isCorrected()) {
								mean += practiceAct.getCalification();
								n++;
							}
						}

					}
					if (!user.isStudent()) {
						mean = mean / n;
						listPractices.get(i).add(mean);
					}
					i++;
				}
				return new ResponseEntity<>(listPractices, HttpStatus.OK);
			}
		}
		/* If program reaches here is that there has been an error */
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
