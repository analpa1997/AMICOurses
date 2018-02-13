package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long courseID;

	private String courseName;

	private String courseLanguage;

	private Date startDate;

	private Date endDate;

	private String courseDescription;

	private String urlImage;

	/*
	 * @OneToMany private List<User> users;
	 */

	public long getCourseID() {
		return courseID;
	}

	public void setCourseID(long courseID) {
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

	/*
	 * public List<User> getUsers() { return users; }
	 * 
	 * public void setUsers(List<User> users) { this.users = users; }
	 */

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", courseName=" + courseName + ", courseLanguage=" + courseLanguage
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", courseDescription=" + courseDescription
				+ ", urlImage=" + urlImage + "]";
	}

	public Course(String courseName, String courseLanguage, Date startDate, Date endDate, String courseDescription,
			String urlImage) {
		super();
		this.courseName = courseName;
		this.courseLanguage = courseLanguage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseDescription = courseDescription;
		this.urlImage = urlImage;
	}

	public Course() {

	}

	public void createCourse() {

	}

	public void deleteCourse() {
	}

}