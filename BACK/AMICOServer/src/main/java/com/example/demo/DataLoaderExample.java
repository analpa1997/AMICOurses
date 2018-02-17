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
import com.example.demo.message_package.Message;
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
//@Order(1)
public class DataLoaderExample implements CommandLineRunner{

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
		 
		List <Course> courses = new ArrayList<>();
				
		/* Courses */
		courses.add(new Course("Introduction to AI", "English", null, null,
			"If you want to learn all about AI, this is our course",
			"introoduction-to-ai.jpg"));
		
		courses.add(new Course("AI Advanced Tips", "English", null, null,
				"Learn all the most advanced stuff related to IA",
				"ai-advanced-tips.jpeg"));
		
		courses.add(new Course("Cocina Moderna", "Español", null, null,
				"Si tienes hambre y no sabes ni freir un huevo este es tu curso.",
				"cocina-moderna.jpg"));
		
		courses.add(new Course("Cortar con tijeras", "Español", null, null,
				"Aprende a cortar con tijeras como un maestro. Este curso intensivo te hará desarollar tu potencial oculto",
				"cortar-con-tijeras.jpg"));
		
		courses.add(new Course("Matematicas Para Gatos", "Miau", null, null,
				"Miau miau miau miau ( +, -, *, /, ...) miau miau miau. Miuau miau",
				"matematicas-para-gatos.jpg"));
		
		courses.add(new Course("Retrato profesional", "Español", null, null,
				"En este curso se abordaran las técnicas más novedosas a la hora de realizar retratos a mano alzada.",
				"retrato-profesional.jpg"));
		
		
		List <User> users = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			users.add(new User("student-" + i, "pass-" + i , "user" + i +"@mail.com", true));
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
		users.add(new User ("amicoTeacher" , "pass" , "amicoTeacher@mail.com", false));
		
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
		
		userRepository.save(users);
			
		/*List<Message> messages = new ArrayList<> ();
		for (int i = 0; i < 5; i++){
			messages.add(new Message("Asunto" + i, "this is a message" + i));
			messages.get(i).setAuthor(users.get(50));
			messages.get(i).getReceivers().add(users.get(50));
			messages.get(i).setSubject(subjects.get(0));
		}*/
		
		
		//messageRepository.save(messages);
		
		
		courseRepository.save(courses);
		
		List <Subject> subjects = new ArrayList <> ();
		subjects.add(new Subject ("History of AI"));
		subjects.add(new Subject ("Uninformed Search"));
		subjects.add(new Subject ("Informed Search"));
		subjects.add(new Subject ("Heuristic Search"));
		subjects.add(new Subject ("Decisision trees"));
		
		subjects.get(0).setCourse(courses.get(0));
		subjects.get(1).setCourse(courses.get(0));
		subjects.get(2).setCourse(courses.get(0));
		subjects.get(3).setCourse(courses.get(0));
		subjects.get(4).setCourse(courses.get(0));

		
		subjectRepository.save(subjects);
		
		
		Exam exam = new Exam ("exam 1");
		exam.setSubject(subjects.get(0));
		
		examRepository.save(exam);
		
		List <Skill> skills = new ArrayList<> ();
		for (int i = 0; i < 5; i++) {
			skills.add(new Skill ("Skill " + i, "Description " + i));
			skills.get(i).setCourse(courses.get(0));
		}
		
		skillRepository.save(skills);
		
		List <StudyItem> studyItems = new ArrayList <> ();
		studyItems.add(new StudyItem(0, "tema 1", 1 , "tema-1.txt"));
		studyItems.add(new StudyItem(0, "tema 2", 2 , "tema-2.txt"));
		studyItems.add(new StudyItem(0, "tema 3", 3 , "tema-3.txt"));
		
		studyItems.get(0).setSubject(subjects.get(0));
		studyItems.get(1).setSubject(subjects.get(0));
		studyItems.get(2).setSubject(subjects.get(0));
		
		studyItemRepository.save(studyItems);
		
		List <Practices> practices = new ArrayList <> ();
		practices.add(new Practices ("practica 1", "practica-1.txt"));
		practices.add(new Practices ("practica 2", "practica-2.txt"));
		
		practices.get(0).setSubject(subjects.get(0));
		practices.get(1).setSubject(subjects.get(0));
		
		practicesRepository.save(practices);
		
		
		
		
		/*No se pueden hacer querys aqui*/
		/*Test Querys*/
		/*/* Test Query. It should retrieve "all the courses of the user-0 */
		/*List<Course> queryCourses = userRepository.findByUsername("User-0").getInscribedCourses();
		for (Course course : queryCourses) {
			System.out.println("Course name : " + course.getName() + " course id: " + course.getCourseID());
		}*/
		
	}
	
}