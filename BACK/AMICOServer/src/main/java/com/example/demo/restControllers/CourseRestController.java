package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;

@RestController
public class CourseRestController {

	@Autowired
	private CourseRepository repository;

	@RequestMapping(value = "/api/courses/", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> allCourses(Pageable pages,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sort", defaultValue = "courseID") String nameField,
			@RequestParam(value = "type", defaultValue = "all") String type,
			@RequestParam(value = "name", defaultValue = "") String nameCourse) {
		Page<Course> pageCourse;
		if (!nameField.equals("")) {
			if (nameField.equals("numberOfUsers"))
				if (type.equals("all"))
					if (nameCourse.equals(""))
						pageCourse = repository.findAll(new PageRequest(page, 10, Sort.Direction.DESC, nameField));
					else
						pageCourse = repository.findByInternalNameContaining(nameCourse,
								new PageRequest(page, 10, Sort.Direction.DESC, nameField));
				else if (nameCourse.equals(""))
					pageCourse = repository.findByType(type, new PageRequest(page, 10, Sort.Direction.DESC, nameField));
				else
					pageCourse = repository.findByTypeAndInternalNameContaining(type, nameCourse,
							new PageRequest(page, 10, Sort.Direction.DESC, nameField));
			else if (type.equals("all"))
				if (nameCourse.equals(""))
					pageCourse = repository.findAll(new PageRequest(page, 10, Sort.Direction.ASC, nameField));
				else
					pageCourse = repository.findByInternalNameContaining(nameCourse,
							new PageRequest(page, 10, Sort.Direction.ASC, nameField));
			else if (nameCourse.equals(""))
				pageCourse = repository.findByType(type, new PageRequest(page, 10, Sort.Direction.ASC, nameField));
			else
				pageCourse = repository.findByTypeAndInternalNameContaining(type, nameCourse,
						new PageRequest(page, 10, Sort.Direction.ASC, nameField));
		} else {
			if (type.equals("all"))
				if (nameCourse.equals(""))
					pageCourse = repository.findAll(new PageRequest(page, 10));
				else
					pageCourse = repository.findByInternalNameContaining(nameCourse, new PageRequest(page, 10));
			else if (nameCourse.equals(""))
				pageCourse = repository.findByType(type, new PageRequest(page, 10));
			else
				pageCourse = repository.findByTypeAndInternalNameContaining(type, nameCourse,
						new PageRequest(page, 10));
		}
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
