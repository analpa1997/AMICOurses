package com.example.demo.restControllers;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.subject.Subject;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;

@RestController
public class MoodleRestController {

	@Autowired
	private CourseRepository repository;

	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	/* MODULES */
	/* POST */
	/* Creates a new module within a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module", method = RequestMethod.POST)
	public Object addModule (@PathVariable String courseInternalName, @PathVariable String subjectInternalNames) {
	
		return null;
	}
	
	/* DELETE */
	/* Creates a new module within a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/module/{module}", method = RequestMethod.DELETE)
	public Object deleteModule (@PathVariable String courseInternalName, @PathVariable String subjectInternalName, @PathVariable Integer module) {
	
		return null;
	}
	
	/* STUDY ITEMS */
	
	/* GET */
	/* Retrieves all the studyItems from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/all", method = RequestMethod.GET)
	public Object getAllStudyItems (@PathVariable String courseInternalName, @PathVariable String subjectInternalName) {
	
		return null;
	}
	
	/* Retrieves the studyItems from a module from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.GET)
	public Object getStudyItemsFromModule (@PathVariable String courseInternalName, @PathVariable String subjectInternalName, @PathVariable Integer module) {
	
		return null;
	}
	
	/* Retrieves only one studyItem from a subject */
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/one/{studyItemID}", method = RequestMethod.GET)
	public Object getOneStudyItem (@PathVariable String courseInternalName, @PathVariable String subjectInternalName, @PathVariable Long studyItemID) {
	
		return null;
	}
	
	/* POST */
	/* Creates a studyItem within a subject and a module*/
	@RequestMapping(value = "/api/moodle/{courseInternalName}/{subjectInternalName}/studyItem/module/{module}", method = RequestMethod.POST)
	public Object createStudyItem (@PathVariable String courseInternalName, @PathVariable String subjectInternalName, @PathVariable Integer module, @RequestParam String itemName,
			@RequestParam String itemType, @RequestParam("itemFile") MultipartFile file) {
	
		return null;
	}
	
	/* DELETE */
	
	
	/*  */
	
	
	
	
	
	
	
	
	
	/* TO DO: refractor all this autentication and checkings into a service*/
	@RequestMapping(value = "/api/moodle/practicesMarks/{courseInternalName}/{subjectInternalName}/practices/", method = RequestMethod.GET)
	public ResponseEntity<List<List<Object>>> allCourses(@PathVariable String courseInternalName,
			@PathVariable String subjectInternalName) {

		User user = sessionUserComponent.getLoggedUser();

		if (user != null) {
			/* Return the marks of every practice */
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

		}
		/* If program reaches here is that there has been an error*/
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
