package com.example.demo.subject_package;

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

import com.example.demo.course_package.Course;
import com.example.demo.exam_package.Exam;
import com.example.demo.message_package.Message;
import com.example.demo.practices_package.Practices;
import com.example.demo.studyItem_package.StudyItem;
import com.example.demo.user_package.User;

@Entity
@Table(name = "Subjects")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long subjectID;

	private String name;

	private String description;

	@ManyToOne
	private Course course;

	@OneToMany(mappedBy = "subject")
	private List<Practices> practiceList = new ArrayList<>();

	@OneToMany(mappedBy = "subject")
	private List<StudyItem> studyItemsList = new ArrayList<>();

	@OneToMany(mappedBy = "subject")
	private List<Exam> exams = new ArrayList<>();

	@OneToMany(mappedBy = "subject")
	private List<Message> messages = new ArrayList<>();

	@ManyToMany
	private List<User> teachers = new ArrayList<>();

	private String internalName;

	/* Constructors */
	public Subject() {
	}

	public Subject(String name) {
		this.name = name;
		this.internalName = name.replaceAll(" ", "-").toLowerCase();
		this.description = "";
	}

	public Subject(String name, String description) {
		this(name);
		this.description = description;
		this.internalName = name.replaceAll(" ", "-").toLowerCase();
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

	public List<Practices> getPracticeList() {
		return practiceList;
	}

	public void setPracticeList(List<Practices> practiceList) {
		this.practiceList = practiceList;
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

}