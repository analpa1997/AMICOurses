package com.example.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;

@RestController
@RequestMapping(value = "/api/courseInformation/")
public class CourseInformationRestController {
	
	@Autowired
	private CourseRepository courseRepository;

	@RequestMapping(value = { "/{internalName}" }, method = RequestMethod.GET)
	public ResponseEntity<Course> courseInformation(@PathVariable String internalName) {
	
		Course course;
			
		course = courseRepository.findByInternalName(internalName);
		if (course != null)
			return new ResponseEntity<>(course, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
