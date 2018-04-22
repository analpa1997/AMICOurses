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
//import com.example.demo.course.Course.BasicInformation;
import com.example.demo.exam.Exam;
import com.example.demo.message.Message;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Subjects")
public class Subject {

	public interface CourseBasicInformation {
	}

	public interface SubjectsBasicInformation {
	}
	
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
	@JsonView(SubjectsBasicInformation.class)
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
		this.setInternalName(name);
		setNumberModules(0);
	}

	/* Methods */

	public void addModule() {
		numberModules++;
	}

	public void deleteModule() {
		numberModules--;
	}

	@Override
	public boolean equals(Object obj2) {

		boolean sameObj = false;

		if (obj2 != null && obj2 instanceof Subject)
			sameObj = subjectID == ((Subject) obj2).subjectID;

		return sameObj;
	}

	public Course getCourse() {
		return course;
	}

	public String getDescription() {
		return description;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public String getInternalName() {
		return internalName;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public String getName() {
		return name;
	}

	public int getNumberModules() {
		return numberModules;
	}

	public List<StudyItem> getStudyItemsList() {
		return studyItemsList;
	}

	public long getSubjectID() {
		return subjectID;
	}

	public List<User> getTeachers() {
		return teachers;
	}

	public List<User> getUsers() {
		return course.getInscribedUsers();
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName.replaceAll(" ", "-").toLowerCase();
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setName(String name) {
		this.name = name;
		this.setInternalName(name);
	}

	public void setNumberModules(int numberModules) {
		this.numberModules = numberModules;
	}

	public void setStudyItemsList(List<StudyItem> studyItemsList) {
		this.studyItemsList = studyItemsList;
	}

	public void setSubjectID(long subjectID) {
		this.subjectID = subjectID;
	}

	public void setTeachers(List<User> teachers) {
		this.teachers = teachers;
	}

}