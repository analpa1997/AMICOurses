package com.example.demo.course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.skill.Skill;
import com.example.demo.skill.SkillRepository;
import com.example.demo.user.SessionUserComponent;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Service
public class CourseService {

	@Autowired
	public SessionUserComponent sessionUserComponent;
	@Autowired
	public CourseRepository courseRepository;
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public SkillRepository skillRepository;

	public Course createCourse(String newName, String newLanguage, String newType, String newSkill1, String newSkill2,
			String newSkill3, Date startDate, Date endDate, String newDescription, MultipartFile file) {
		Course course = null;
		User user = sessionUserComponent.getLoggedUser();
		if (user != null && user.isAdmin()) {
			course = courseRepository.findByName(newName);

			if (course == null)
				if (!newName.isEmpty() && !newLanguage.isEmpty() && !newDescription.isEmpty() && !newType.isEmpty()
						&& !newSkill1.isEmpty()) {
					course = new Course(newName, newLanguage, newDescription, newType, "");
					course.setStartDate(startDate);
					course.setEndDate(endDate);
					course.getInscribedUsers().add(user);
					user.getInscribedCourses().add(course);

					courseRepository.save(course);
					userRepository.save(user);
					course = courseRepository.findByName(course.getName());

					Skill skill1 = new Skill(newSkill1);
					skill1.setCourse(course);
					skillRepository.save(skill1);

					if (!newSkill2.isEmpty()) {
						Skill skill2 = new Skill(newSkill2);
						skill2.setCourse(course);
						skillRepository.save(skill2);
					}

					if (!newSkill3.isEmpty()) {
						Skill skill3 = new Skill(newSkill3);
						skill3.setCourse(course);
						skillRepository.save(skill3);
					}

					if (!file.isEmpty())
						try {
							Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
									"files/image/courses/" + course.getCourseID() + "/");
							if (!Files.exists(FILES_FOLDER))
								Files.createDirectories(FILES_FOLDER);

							String fileName = "course-" + course.getCourseID() + ".jpg";

							File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
							file.transferTo(uploadedFile);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
				}
		}
		return course;
	}

}
