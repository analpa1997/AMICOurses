package com.example.demo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.course_package.Course;
import com.example.demo.course_package.CourseRepository;
import com.example.demo.exam_package.Exam;
import com.example.demo.exam_package.ExamRepository;
import com.example.demo.message_package.MessageRepository;
import com.example.demo.practices_package.Practices;
import com.example.demo.practices_package.PracticesRepository;
import com.example.demo.skill_package.Skill;
import com.example.demo.skill_package.SkillRepository;
import com.example.demo.studyItem_package.StudyItem;
import com.example.demo.studyItem_package.StudyItemRepository;
import com.example.demo.subject_package.Subject;
import com.example.demo.subject_package.SubjectRepository;
import com.example.demo.user_package.User;
import com.example.demo.user_package.UserRepository;

@Component
@Order(1)
public class DataLoaderExample {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private PracticesRepository practicesRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@PostConstruct
	public void init() {

		List<Course> courses = new ArrayList<>();

		/* Courses */
		courses.add(new Course("Introduction to AI", "English", new Date(119, 0, 31), new Date(119, 6, 30),
				"If you want to learn all about AI, this is our course", "Computer Science",
				"introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips", "English", "Learn all the most advanced stuff related to IA",
				"Computer Science", "ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "Cooking", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"HandWork", "cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "Maths",
				"matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"Photograph", "retrato-profesional.jpg"));
		courses.add(
				new Course("Introduction to AI2", "English", "If you want to learn all about AI, this is our course",
						"Computer Science", "introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips2", "English", "Learn all the most advanced stuff related to IA",
				"Computer Science", "ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna2", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "Cooking", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras2", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"HandWork", "cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos2", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "Maths",
				"matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional2", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"Drawing", "retrato-profesional.jpg"));
		courses.add(
				new Course("Introduction to AI3", "English", "If you want to learn all about AI, this is our course",
						"Computer Science", "introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips3", "English", "Learn all the most advanced stuff related to IA",
				"Computer Science", "ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna3", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "Cooking", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras3", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"HandWork", "cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos3", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "Maths",
				"matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional3", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"Drawing", "retrato-profesional.jpg"));

		List<User> users = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			users.add(new User("student-" + i, "pass-" + i, "user" + i + "@mail.com", true));
		}

		for (int i = 0; i < 35; i++) {
			courses.get(0).getInscribedUsers().add(users.get(i));
			users.get(i).getInscribedCourses().add(courses.get(0));

		}

		for (int i = 0; i < 20; i++) {
			courses.get(1).getInscribedUsers().add(users.get(i));
			users.get(i).getInscribedCourses().add(courses.get(1));
		}

		for (int i = 30; i < 50; i++) {
			courses.get(2).getInscribedUsers().add(users.get(i));
			users.get(i).getInscribedCourses().add(courses.get(2));
		}

		for (int i = 0; i < 18; i++) {
			courses.get(3).getInscribedUsers().add(users.get(i));
			users.get(i).getInscribedCourses().add(courses.get(3));
		}

		for (int i = 20; i < 35; i++) {
			courses.get(4).getInscribedUsers().add(users.get(i));
			users.get(i).getInscribedCourses().add(courses.get(4));
		}

		users.add(new User("amico", "pass", "amicourses@mail.com", true));
		users.add(new User("amicoTeacher", "pass", "amicoTeacher@mail.com", false));
		users.add(new User("amicoTeacher2", "pass", "amicoTeacher2@mail.com", false));
		users.add(new User("amicoTeacher3", "pass", "amicoTeacher3@mail.com", false));
		users.add(new User("amicoTeacher4", "pass", "amicoTeacher4@mail.com", false));
		users.add(new User("amicoTeacher5", "pass", "amicoTeacher5@mail.com", false));
		users.add(new User("amicoTeacher6", "pass", "amicoTeacher6@mail.com", false));

		courses.get(0).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(0));
		courses.get(1).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(1));
		courses.get(2).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(2));
		courses.get(3).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(3));
		courses.get(4).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(4));
		courses.get(5).getInscribedUsers().add(users.get(50));
		users.get(50).getInscribedCourses().add(courses.get(5));

		courses.get(0).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(0));
		courses.get(1).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(1));
		courses.get(2).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(2));
		courses.get(3).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(3));
		courses.get(4).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(4));
		courses.get(5).getInscribedUsers().add(users.get(51));
		users.get(51).getInscribedCourses().add(courses.get(5));

		users.get(50).setCity("New York");
		users.get(50).setCountry("United States of America");
		users.get(50).setUserAddress("Under the Brooklyn Bridge");
		users.get(50).setUserFirstName("Amico");
		users.get(50).setUserLastName("Fernandez");
		users.get(50).setInterests("Sleeping and watching tv.");

		for (Course c : courses) {
			c.setNumberOfUsers(c.getInscribedUsers().size());
		}

		List<Subject> subjects = new ArrayList<>();
		subjects.add(new Subject("History of AI", "A short history for the AI"));
		subjects.add(new Subject("Uninformed Search", "sdvdsvsdvds"));
		subjects.add(new Subject("Informed Search", "vnkvnirnfñk"));
		subjects.add(new Subject("Heuristic Search", "kflsdbflsdbf"));
		subjects.add(new Subject("Decisision trees", "nñnovdsnopsd"));

		subjects.get(0).getTeachers().add(users.get(51));
		subjects.get(1).getTeachers().add(users.get(51));
		subjects.get(2).getTeachers().add(users.get(51));
		subjects.get(3).getTeachers().add(users.get(51));
		subjects.get(4).getTeachers().add(users.get(51));

		subjects.get(0).getTeachers().add(users.get(52));
		subjects.get(0).getTeachers().add(users.get(53));
		subjects.get(1).getTeachers().add(users.get(52));

		users.get(51).getTeaching().add(subjects.get(0));
		users.get(52).getTeaching().add(subjects.get(0));
		users.get(53).getTeaching().add(subjects.get(0));
		users.get(51).getTeaching().add(subjects.get(1));
		users.get(52).getTeaching().add(subjects.get(1));
		users.get(51).getTeaching().add(subjects.get(2));
		users.get(51).getTeaching().add(subjects.get(3));
		users.get(51).getTeaching().add(subjects.get(4));

		courses.get(1).setCompleted(true);
		courses.get(3).setCompleted(true);

		/*
		 * List <Course> completed = new ArrayList<>(); for (Course c : courses) { if
		 * (c.isCompleted()) { completed.add(c); } }
		 */

		// users.get(50).getInscribedCourses().removeAll(completed);

		// users.get(50).getCompletedCourses().addAll(completed);

		courseRepository.save(courses);
		
		userRepository.save(users);

		/*
		 * List<Message> messages = new ArrayList<> (); for (int i = 0; i < 5; i++){
		 * messages.add(new Message("Asunto" + i, "this is a message" + i));
		 * messages.get(i).setAuthor(users.get(50));
		 * messages.get(i).getReceivers().add(users.get(50));
		 * messages.get(i).setSubject(subjects.get(0)); }
		 */

		// messageRepository.save(messages);

		subjects.get(0).setCourse(courses.get(0));
		subjects.get(1).setCourse(courses.get(0));
		subjects.get(2).setCourse(courses.get(0));
		subjects.get(3).setCourse(courses.get(0));
		subjects.get(4).setCourse(courses.get(0));

		subjectRepository.save(subjects);

		Exam exam = new Exam("exam 1");
		exam.setSubject(subjects.get(0));

		examRepository.save(exam);

		List<Skill> skills = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			skills.add(new Skill("Skill " + i, "Description " + i));
			skills.get(i).setCourse(courses.get(0));
		}

		skillRepository.save(skills);

		List<StudyItem> studyItems = new ArrayList<>();
		studyItems.add(new StudyItem("pdf", "Theme 1", 1, "studyItem-1", "theme-1.txt"));
		studyItems.add(new StudyItem("pdf", "Theme 1 exercicies", 1, "studyItem-2", "theme-1 exercices.txt"));
		studyItems.add(new StudyItem("video", "Theme 2", 1, "studyItem-3", "theme-2.txt"));
		studyItems.add(new StudyItem("word", "Theme 2 exercicies", 1, "studyItem-4", "theme-2-ex.txt"));
		studyItems.add(new StudyItem("pdf", "Theme 3", 1, "studyItem-5", "theme-3.txt"));
		studyItems.add(new StudyItem("pdf", "Theme 4", 2, "studyItem-6", "theme-4.txt"));
		studyItems.add(new StudyItem("pdf", "Theme 4 exercices", 2, "studyItem-7", "theme-4-ex.txt"));
		studyItems.add(new StudyItem("word", "Theme 5", 3, "studyItem-8", "theme-5.txt"));
		studyItems.add(new StudyItem("", "Theme 5 exercicies", 3, "studyItem-9", "theme-5-ex.txt"));
		studyItems.add(new StudyItem("", "Theme 6", 3, "studyItem-10", "theme-6.txt"));
		studyItems.add(new StudyItem("video", "Theme 6 exercicies", 3, "studyItem-11", "theme-6-ex.txt"));

		studyItems.add(new StudyItem("pdf", "Practice 1", -1, "studyItem-12", "Practice 1.txt"));
		studyItems.get(studyItems.size() - 1).setPractice(true);

		studyItems.add(new StudyItem("pdf", "Practice 2", -1, "studyItem-13", "Practice 2.txt"));
		studyItems.get(studyItems.size() - 1).setPractice(true);

		/* There are 3 modules */
		subjects.get(0).addModule();
		subjects.get(0).addModule();
		subjects.get(0).addModule();

		for (StudyItem study : studyItems) {
			study.setSubject(subjects.get(0));
		}

		studyItemRepository.save(studyItems);
		subjectRepository.save(subjects.get(0));

		List<Practices> practices = new ArrayList<>();
		practices.add(new Practices("practice 1", "practice-1.txt"));
		practices.get(0).setOwner(users.get(50));
		practices.get(0).setCalification(7.3);
		practices.get(0).setPresented(true);
		practices.get(0).setCorrected(true);
		practices.get(0).setStudyItem(studyItems.get(studyItems.size() - 2));
		studyItems.get(studyItems.size() - 2).getPractices().add(practices.get(0));

		practices.add(new Practices("practice 1", "practice-2.txt"));
		practices.get(1).setOwner(users.get(5));
		practices.get(1).setCalification(5.25);
		practices.get(1).setCorrected(true);
		practices.get(1).setPresented(true);
		practices.get(1).setStudyItem(studyItems.get(studyItems.size() - 2));
		studyItems.get(studyItems.size() - 2).getPractices().add(practices.get(1));

		practices.add(new Practices("practice 2", "practice-3.txt"));
		practices.get(2).setOwner(users.get(5));
		practices.get(2).setPresented(true);
		practices.get(2).setStudyItem(studyItems.get(studyItems.size() - 1));
		studyItems.get(studyItems.size() - 1).getPractices().add(practices.get(2));

		practicesRepository.save(practices);
		studyItemRepository.save(studyItems);

	}

}