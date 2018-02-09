package com.example.demo.entities;

import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Subjects")
public class Subjects {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subjectID;

	@OneToOne
	private Course course;

	private String subjectName;

	private int creditsNumber;

	private Stack subjectsDescription;

	private Date firstExamDate;

	private Date lastExamDate;

	@OneToMany
	private List practiceList;

	@OneToMany
	private List studentItemsList;

	public int getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getCreditsNumber() {
		return creditsNumber;
	}

	public void setCreditsNumber(int creditsNumber) {
		this.creditsNumber = creditsNumber;
	}

	public Stack getSubjectsDescription() {
		return subjectsDescription;
	}

	public void setSubjectsDescription(Stack subjectsDescription) {
		this.subjectsDescription = subjectsDescription;
	}

	public Date getFirstExamDate() {
		return firstExamDate;
	}

	public void setFirstExamDate(Date firstExamDate) {
		this.firstExamDate = firstExamDate;
	}

	public Date getLastExamDate() {
		return lastExamDate;
	}

	public void setLastExamDate(Date lastExamDate) {
		this.lastExamDate = lastExamDate;
	}

	public List getPracticeList() {
		return practiceList;
	}

	public void setPracticeList(List practiceList) {
		this.practiceList = practiceList;
	}

	public List getStudentItemsList() {
		return studentItemsList;
	}

	public void setStudentItemsList(List studentItemsList) {
		this.studentItemsList = studentItemsList;
	}

	private void createSubject() {
	}

}