package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Students")
public class Student extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;

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

}