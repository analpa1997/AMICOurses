package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Professors")
public class Professor extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userID;

	@OneToMany
	private int subjectsList;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getSubjectsList() {
		return subjectsList;
	}

	public void setSubjectsList(int subjectsList) {
		this.subjectsList = subjectsList;
	}

	public void createProfessor() {
	}

	public void updateProfessor() {
	}

	public void deleteProfessor() {
	}

	public void getProfessor() {
	}

}