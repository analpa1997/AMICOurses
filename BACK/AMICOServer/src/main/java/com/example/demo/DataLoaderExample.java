package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Component
public class DataLoaderExample implements CommandLineRunner{

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		//List <Skill> skills = new ArrayList <> ();
		
		List <Course> courses = new ArrayList<>();
		
		List <User> users = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			courses.add(new Course("Introduction to AI", "English", null, null,
				"If you want to learn all about AI, this is our course",
				"https://i.blogs.es/09f8d5/650_1200/450_1000.jpg"));
	
			courses.add(new Course("AI: Advanced Tips", "English", null, null,
				"If you want to learn all about AI, this is our course",
				"http://www.globalmediait-cr.com/wp-content/uploads/2016/08/BBVA-OpenMind-El-futuro-de-la-inteligencia-artificial-y-la-cibern%C3%A9tica.jpg"));
		}
		
		for (int i = 0; i < 5; i++) {
			users.add(new User("User-" + i, "pass", "hola" + i +"@mail.com", true));
		}
		
		courses.get(0).getInscribedUsers().add(users.get(0));
		courses.get(1).getInscribedUsers().add(users.get(0));
		courses.get(4).getInscribedUsers().add(users.get(0));
		courses.get(6).getInscribedUsers().add(users.get(0));
		
		
		userRepository.save(users);
		courseRepository.save(courses);
		
	}
	
}