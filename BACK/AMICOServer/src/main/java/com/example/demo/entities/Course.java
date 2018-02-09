package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int courseID;

	private String courseName;

	private String courseLanguage;

	private Date startDate;

	private Date endDate;

	private String courseDescription;

	@OneToMany
	private List subjects;

	@OneToMany
	private List skills;

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseLanguage() {
		return courseLanguage;
	}

	public void setCourseLanguage(String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public List getSubjects() {
		return subjects;
	}

	public void setSubjects(List subjects) {
		this.subjects = subjects;
	}

	public List getSkills() {
		return skills;
	}

	public void setSkills(List skills) {
		this.skills = skills;
	}

	public void createCourse() {
	}

	public void deleteCourse() {
	}

}