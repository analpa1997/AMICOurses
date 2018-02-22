package com.example.demo.restControllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;

@RestController
public class CourseRestController {

	@Autowired
	private CourseRepository repository;

	@RequestMapping(value = "/api/listCourses/", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> allCourses(Pageable pages) {
		Page<Course> pageCourse = repository.findAll(new PageRequest(0, 10));
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/listCourses/p{numPage}", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> moreCourses(Pageable pages, @PathVariable int numPage) {
		Page<Course> pageCourse = repository.findAll(new PageRequest(numPage, 10));
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/listCourses/byNewest/", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> allCoursesNewest() {
		Page<Course> pageCourse = repository.findAll(new PageRequest(0, 10, Sort.Direction.ASC, "startDate"));
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/listCourses/byNewest/p{numPage}", method = RequestMethod.GET)
	public ResponseEntity<Page<Course>> coursesSortedByNewest(Pageable pages, @PathVariable int numPage) {
		Page<Course> pageCourse = repository.findAll(new PageRequest(numPage, 10, Sort.Direction.ASC, "startDate"));
		if (pageCourse != null)
			return new ResponseEntity<>(pageCourse, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/api/listCourses/{internalName}", method = RequestMethod.GET)
	public ResponseEntity<Course> oneCourse(@PathVariable String internalName) {
		Course c = repository.findByInternalName(internalName);
		if (c != null)
			return new ResponseEntity<>(c, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
