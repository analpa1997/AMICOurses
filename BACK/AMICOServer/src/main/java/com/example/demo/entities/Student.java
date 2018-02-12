package com.example.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Students")
public class Student extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;

	@OneToMany
	private List<Course> courseList;

	public void createStudent() {
	}

	public void updateStudent() {
	}

	public void deleteStudent() {
	}

	public void getStudent() {
	}

	@Override
	public long getUserID() {
		return userID;
	}

	@Override
	public void setUserID(long userID) {
		this.userID = userID;
	}

	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}

}