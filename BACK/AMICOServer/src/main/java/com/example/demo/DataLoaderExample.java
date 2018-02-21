package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
// @Order(1)
public class DataLoaderExample implements CommandLineRunner {

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

	@Override
	public void run(String... arg0) throws Exception {

		List<Course> courses = new ArrayList<>();

		/* Courses */
		courses.add(new Course("Introduction to AI", "English", "If you want to learn all about AI, this is our course",
				"introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips", "English", "Learn all the most advanced stuff related to IA",
				"ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"retrato-profesional.jpg"));
		courses.add(new Course("Introduction to AI2", "English", "If you want to learn all about AI, this is our course",
				"introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips2", "English", "Learn all the most advanced stuff related to IA",
				"ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna2", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras2", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos2", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional2", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"retrato-profesional.jpg"));
		courses.add(new Course("Introduction to AI3", "English", "If you want to learn all about AI, this is our course",
				"introoduction-to-ai.jpg"));

		courses.add(new Course("AI Advanced Tips3", "English", "Learn all the most advanced stuff related to IA",
				"ai-advanced-tips.jpeg"));

		courses.add(new Course("Cocina Moderna3", "Español",
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.", "cocina-moderna.jpg"));

		courses.add(new Course("Cortar con tijeras3", "Español",
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"cortar-con-tijeras.jpg"));

		courses.add(new Course("Matematicas Para Gatos3", "Miau",
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau", "matematicas-para-gatos.jpg"));

		courses.add(new Course("Retrato profesional3", "Español",
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"retrato-profesional.jpg"));

		List<User> users = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			users.add(new User("student-" + i, "pass-" + i, "user" + i + "@mail.com", true));
		}

		for (int i = 0; i < 35; i++) {
			courses.get(0).getInscribedUsers().add(users.get(i));
		}

		for (int i = 0; i < 20; i++) {
			courses.get(1).getInscribedUsers().add(users.get(i));
		}

		for (int i = 30; i < 50; i++) {
			courses.get(2).getInscribedUsers().add(users.get(i));
		}

		for (int i = 0; i < 18; i++) {
			courses.get(3).getInscribedUsers().add(users.get(i));
		}

		for (int i = 20; i < 35; i++) {
			courses.get(4).getInscribedUsers().add(users.get(i));
		}

		users.add(new User("amico", "pass", "amicourses@mail.com", true));
		users.add(new User("amicoTeacher", "pass", "amicoTeacher@mail.com", false));
		users.add(new User("amicoTeacher2", "pass", "amicoTeacher2@mail.com", false));
		users.add(new User("amicoTeacher3", "pass", "amicoTeacher3@mail.com", false));
		users.add(new User("amicoTeacher4", "pass", "amicoTeacher4@mail.com", false));
		users.add(new User("amicoTeacher5", "pass", "amicoTeacher5@mail.com", false));
		users.add(new User("amicoTeacher6", "pass", "amicoTeacher6@mail.com", false));

		courses.get(0).getInscribedUsers().add(users.get(50));
		courses.get(1).getInscribedUsers().add(users.get(50));
		courses.get(2).getInscribedUsers().add(users.get(50));
		courses.get(3).getInscribedUsers().add(users.get(50));
		courses.get(4).getInscribedUsers().add(users.get(50));
		courses.get(5).getInscribedUsers().add(users.get(50));

		courses.get(0).getInscribedUsers().add(users.get(51));
		courses.get(1).getInscribedUsers().add(users.get(51));
		courses.get(2).getInscribedUsers().add(users.get(51));
		courses.get(3).getInscribedUsers().add(users.get(51));
		courses.get(4).getInscribedUsers().add(users.get(51));
		courses.get(5).getInscribedUsers().add(users.get(51));

		users.get(50).setCity("New York");
		users.get(50).setCountry("United States of America");
		users.get(50).setRole("Play Fortnite");
		users.get(50).setUserAddress("Under the Brooklyn Bridge");
		users.get(50).setUserFirstName("Amico");
		users.get(50).setUserLastName("Fernandez");
		users.get(50).setInterests("Sleeping and watching tv.");

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

		userRepository.save(users);

		courseRepository.save(courses);

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
		studyItems.add(new StudyItem("file-alt", "Theme 1", 1, "theme-1.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 1 exercicies", 1, "theme-1.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 2", 1, "theme-1.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 2 exercicies", 1, "theme-1.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 3", 1, "theme-1.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 4", 2, "theme-2.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 4 exercices", 2, "theme-2.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 5", 3, "theme-3.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 5 exercicies", 3, "theme-3.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 6", 3, "theme-3.txt"));
		studyItems.add(new StudyItem("file-alt", "Theme 6 exercicies", 3, "theme-3.txt"));
		
		/* There are 3 modules*/
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
		practices.add(new Practices("practice 2", "practice-2.txt"));

		practices.get(0).setSubject(subjects.get(0));
		practices.get(1).setSubject(subjects.get(0));

		practicesRepository.save(practices);

	}

}