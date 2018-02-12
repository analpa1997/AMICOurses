package com.example.demo.entities;

import java.util.List;

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
	private long userID;

	@OneToMany
	private List<Subject> subjectsList;

	@Override
	public long getUserID() {
		return userID;
	}

	@Override
	public void setUserID(long userID) {
		this.userID = userID;
	}

	public List getSubjectsList() {
		return subjectsList;
	}

	public void setSubjectsList(List subjectsList) {
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