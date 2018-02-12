package com.example.demo;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Course;

@RestController
public class CourseController {

	@RequestMapping("/testCourse")
	public Course allCourses() {
		return new Course("HTML Introduction", "English", new Date(), new Date(), null, "http://www.google.es", null,
				null);
	}
}
