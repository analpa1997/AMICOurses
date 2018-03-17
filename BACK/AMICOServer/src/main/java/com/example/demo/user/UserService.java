package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.practices.PracticesService;
import com.example.demo.subject.Subject;

@Service
public class UserService {

	int MAXLENGTH = 20;
	int MINLENGTH = 2;

	int MAXLENGTHADDRESS = 35;
	int LENGTHPHONE = 9;
	int MAXLENGTHINTERESTS = 50;
	int MAXLENGTHURL = 80;

	@Autowired
	UserRepository userRepository;
	@Autowired
	PracticesRepository practicesRepository;
	@Autowired
	private PracticesService practicesService;

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

	public User updateUser(String userInternalName, User user) {

		User u = userRepository.findByInternalName(userInternalName);

		if (u != null) {
			String username = user.getUsername();
			String password = user.getPassword();
			String userMail = user.getUserMail();
			String userFirstName = user.getUserFirstName();
			String userLastName = user.getUserLastName();
			String userAddress = user.getUserAddress();
			String city = user.getCity();
			String country = user.getCountry();
			Integer phoneNumber = (Integer) user.getPhoneNumber();
			String interests = user.getInterests();
			String urlProfileImage = user.getUrlProfileImage();

			if (username != null)
				if (correctName(username) && (userRepository.findByUsername(username) == null))
					u.setUsername(username);

			if (password != null)
				u.setPassword(password);

			/*
			 * if ((password != null) && (repeatPassword != null)) if
			 * (passwordMatch(password, repeatPassword)) u.setPassword(password);
			 */

			if (userMail != null)
				if (isValidEmailAddress(userMail))
					u.setUserMail(userMail);

			if (userFirstName != null)
				if ((MINLENGTH < userFirstName.length()) && (MAXLENGTH > userFirstName.length()))
					u.setUserFirstName(userFirstName);

			if (userLastName != null)
				if ((MINLENGTH < userLastName.length()) && (MAXLENGTH > userLastName.length()))
					u.setUserLastName(userLastName);

			if (userAddress != null)
				if (userAddress.length() < MAXLENGTHADDRESS)
					u.setUserAddress(userAddress);

			if (city != null)
				if (city.length() < MAXLENGTH)
					u.setCity(city);

			if (country != null)
				if (country.length() < MAXLENGTH)
					u.setCountry(country);

			if (phoneNumber != null)
				if (phoneNumber.toString().length() == LENGTHPHONE)
					u.setPhoneNumber((int) phoneNumber);

			if (interests != null)
				if (interests.length() < MAXLENGTHINTERESTS)
					u.setInterests(interests);

			if (urlProfileImage != null)
				if (urlProfileImage.length() < MAXLENGTHURL)
					u.setUrlProfileImage(urlProfileImage);

			return u;

		}

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

		List<Practices> practices = practicesRepository.findByOwner(userToRemove);
		for (Practices p : practices)
			practicesService.deletePracticeSubmission(p.getStudyItem(), p);
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