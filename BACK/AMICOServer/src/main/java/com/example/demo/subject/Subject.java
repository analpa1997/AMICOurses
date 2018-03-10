package com.example.demo.subject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.course.Course;
import com.example.demo.course.Course.BasicInformation;
//import com.example.demo.course.Course.BasicInformation;
import com.example.demo.exam.Exam;
import com.example.demo.message.Message;
import com.example.demo.practices.Practices;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Subjects")
public class Subject {
	
	public interface SubjectsBasicInformation {}
	public interface CourseBasicInformation {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@JsonView(SubjectsBasicInformation.class)
	private long subjectID;

	@JsonView(SubjectsBasicInformation.class)
	private String name;

	@JsonView(SubjectsBasicInformation.class)
	private String description;
	
	@JsonView(CourseBasicInformation.class)
	@ManyToOne
	private Course course;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject")
	private List<StudyItem> studyItemsList = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "subject")
	private List<Exam> exams = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "subject")
	private List<Message> messages = new ArrayList<>();
	@JsonIgnore
	@ManyToMany
	private List<User> teachers = new ArrayList<>();

	private String internalName;
	
	private int numberModules;

	/* Constructors */
	public Subject() {
	}

	public Subject(String name) {
		this(name, "");
	}

	public Subject(String name, String description) {
		this.name = name;
		this.description = description;
		this.internalName = name.replaceAll(" ", "-").toLowerCase();
		setNumberModules(0);
	}

	/* Methods */

	public long getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(long subjectID) {
		this.subjectID = subjectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<User> getUsers() {
		return this.course.getInscribedUsers();
	}

	public List<StudyItem> getStudyItemsList() {
		return studyItemsList;
	}

	public void setStudyItemsList(List<StudyItem> studyItemsList) {
		this.studyItemsList = studyItemsList;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public List<User> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<User> teachers) {
		this.teachers = teachers;
	}

	public int getNumberModules() {
		return numberModules;
	}

	public void setNumberModules(int numberModules) {
		this.numberModules = numberModules;
	}
	
	public void addModule () {
		numberModules++;
	}
	
	public void deleteModule () {
		numberModules--;
	}

}