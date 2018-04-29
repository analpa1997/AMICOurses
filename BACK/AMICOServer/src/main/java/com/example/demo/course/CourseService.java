package com.example.demo.course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exam.Exam;
import com.example.demo.exam.ExamRepository;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.skill.Skill;
import com.example.demo.skill.SkillRepository;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
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
	@Autowired
	public SubjectRepository subjectRepository;
	@Autowired
	public ExamRepository examRepository;
	@Autowired
	public StudyItemRepository studyItemRepository;
	@Autowired
	public PracticesRepository practicesRepository;

	public Course createCourse(Course course) {
		Course courseAux = null;
		courseAux = courseRepository.findByName(course.getName());
		if (courseAux == null) {
			courseAux = course;
			courseRepository.save(courseAux);
		}
		return courseAux;
	}

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

					this.addImg(course, file);
				}
		}
		return course;
	}

	public Course addImg(Course course, MultipartFile file) {
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
		return course;
	}

	public Course deleteCourse(Course deletedCourse) {
		Course courseAux = deletedCourse;
		List<Subject> deletedSubjects = new ArrayList();
		List<Skill> deletedSkills = new ArrayList();
		for (User u : deletedCourse.getInscribedUsers()) {
			u.getInscribedCourses().remove(deletedCourse);
			userRepository.save(u);
		}
		for (Subject s : deletedCourse.getSubjects()) {
			deletedSubjects.add(s);
			List<Exam> removedExams = new ArrayList();
			List<StudyItem> removedStudyItems = new ArrayList();
			List<Practices> removedPractices;
			for (Exam e : s.getExams())
				removedExams.add(e);
			s.getExams().removeAll(removedExams);
			examRepository.delete(removedExams);
			for (StudyItem sI : s.getStudyItemsList()) {
				removedStudyItems.add(sI);
				if (sI.isPractice()) {
					removedPractices = new ArrayList();
					for (Practices p : sI.getPractices())
						removedPractices.add(p);
					sI.getPractices().removeAll(removedPractices);
					practicesRepository.delete(removedPractices);
				}
			}
			s.getStudyItemsList().removeAll(removedStudyItems);
			studyItemRepository.delete(removedStudyItems);
		}
		deletedCourse.getSubjects().removeAll(deletedSubjects);
		subjectRepository.delete(deletedSubjects);
		for (Skill s : deletedCourse.getSkills())
			deletedSkills.add(s);
		deletedCourse.getSkills().removeAll(deletedSkills);
		skillRepository.delete(deletedSkills);
		courseRepository.delete(deletedCourse);
		return courseAux;
	}

	public Course editCourse(Course newCourse) {
		Course courseAux = courseRepository.getOne(newCourse.getCourseID());
		if (courseAux != null) {
			courseAux.setName(newCourse.getName());
			courseAux.setCourseDescription(newCourse.getCourseDescription());
			courseAux.setCourseLanguage(newCourse.getCourseLanguage());
			courseAux.setType(newCourse.getType());
			courseAux.setStartDateString(courseAux.getStartDateString());
			courseAux.setEndDate(courseAux.getEndDate());
		}
		courseRepository.save(courseAux);
		return courseAux;
	}

	public Path getImgPath(Course course) {
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/image/courses/" + course.getCourseID() + "/");
		Path image = FILES_FOLDER.resolve("course-" + course.getCourseID() + ".jpg");
		return image;
	}

}
