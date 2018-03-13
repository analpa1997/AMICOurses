package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.subject.Subject;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User checkUser(String username, String password, String repeatPassword, String userMail, boolean admin) {
		if (passwordMatch(password, repeatPassword) && correctName(username) && isValidEmailAddress(userMail)
				&& (userRepository.findByUsername(username) == null)
				&& (userRepository.findByUserMail(userMail) == null)) {
			User u = new User(username, password, userMail, !admin);
			userRepository.save(u);
			return u;
		} else
			return null;
	}

	public User deleteUser(String userInternalName) {
		User userToRemove = userRepository.findByInternalName(userInternalName);

		List<Course> coursesToRemove = new ArrayList<>();
		for (Course courseAct : userToRemove.getInscribedCourses()) {
			List<Subject> subjectsToRemove = new ArrayList<>();
			for (Subject subjectAct : courseAct.getSubjects()) {
				subjectAct.getTeachers().remove(userToRemove);
			}
			userToRemove.getTeaching().removeAll(subjectsToRemove);
			courseAct.getInscribedUsers().remove(userToRemove);
			coursesToRemove.add(courseAct);
		}

		userToRemove.getInscribedCourses().removeAll(coursesToRemove);
		userToRemove.getCompletedCourses().removeAll(coursesToRemove);
		userRepository.delete(userToRemove);

		return userToRemove;
	}

	public boolean passwordMatch(String s1, String s2) {
		return (Objects.equals(s1, s2) && s1.length() > 7 && s1.length() < 15);
	}

	public boolean correctName(String s) {
		return (s.length() > 4 && s.length() < 16 && s.matches("^[a-zA-Z0-9_-]*$"));
	}

	public boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}