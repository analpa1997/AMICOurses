package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import com.example.demo.subject.SubjectService;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class SubjectRestController {
	
	@Autowired
	private SessionUserComponent sessionUserComponent;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	//POST
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value = "/api/subjects/{courseInternalName}/subjects", method = RequestMethod.POST)
	public ResponseEntity<Subject> newSubject(@PathVariable String courseInternalName, @RequestParam String subjectName){
		User u = sessionUserComponent.getLoggedUser();
		if(u!=null && u.isAdmin()) {
			Course c = courseRepository.findByInternalName(courseInternalName);
			if(c!=null && !subjectName.isEmpty()) {
				Subject s = new Subject(subjectName);
				subjectService.createSubject(c,s);
				return new ResponseEntity<>(s, HttpStatus.CREATED);
			}
			else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value="/api/subjects/{courseInternalName}/{subjectInternalName}", method = RequestMethod.DELETE)
	public ResponseEntity<Subject> deleteSubject(@PathVariable String courseInternalName, @PathVariable String subjectInternalName){
		User u = sessionUserComponent.getLoggedUser();
		if(u != null && u.isAdmin()) {
			if(!subjectInternalName.isEmpty() && !courseInternalName.isEmpty()) {
				Course c = courseRepository.findByInternalName(courseInternalName);
				Subject s = subjectRepository.findByInternalName(subjectInternalName);
				if(c.getSubjects().contains(s)) {
					subjectService.deleteSubject(s, c);
					return new ResponseEntity<>(HttpStatus.OK);
				}
				
				else return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	//PUT
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value="/api/subjects/{courseInternalName}/{subjectInternalName}", method = RequestMethod.PUT)
	public ResponseEntity<Subject> updateSubject(@PathVariable String courseInternalName, @PathVariable String subjectInternalName,
				@RequestBody Subject subject){
		User u = sessionUserComponent.getLoggedUser();
		if(u != null && u.isAdmin()) {
			if(!subjectInternalName.isEmpty() && !courseInternalName.isEmpty()) {
				Course c = courseRepository.findByInternalName(courseInternalName);
				Subject updSubj = subjectRepository.findByInternalName(subjectInternalName);
				if(c != null && updSubj != null && c.getSubjects().contains(updSubj)) {
					subjectService.updateSubject(c,updSubj,subject);
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	//FIND ALL SUBJECTS IN A COURSE
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value = "/api/subjects/{courseInternalName}/subjects/all", method = RequestMethod.GET)
	public ResponseEntity<Page<Subject>> allSubjects(Pageable pages, @PathVariable String courseInternalName,
			@RequestParam(value = "page", defaultValue = "0") int page){
		Page<Subject> pageSubject;
		Course c = courseRepository.findByInternalName(courseInternalName);
		if(c!=null) {
			pageSubject = subjectRepository.findByCourse(c, new PageRequest(page, 10));
		}
		else pageSubject = null;
		if(pageSubject != null) {
			return new ResponseEntity<>(pageSubject, HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//FIND A SUBJECT WITHIN A COURSE
	@JsonView(Subject.SubjectsBasicInformation.class)
	@RequestMapping(value="/api/subjects/{courseInternalName}/{subjectInternalName}", method = RequestMethod.GET)
	public ResponseEntity<Subject> findSubject(@PathVariable String courseInternalName, @PathVariable String subjectInternalName){
		Subject subject = subjectRepository.findByInternalName(subjectInternalName);
		if(subject != null) return new ResponseEntity<>(subject, HttpStatus.FOUND);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
