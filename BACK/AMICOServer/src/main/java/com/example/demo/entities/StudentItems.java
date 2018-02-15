package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StudentItems")
public class StudentItems {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentItemID;

	private int studentItemType;

	private String studentItemName;

	private String studentItemPath;

	public long getStudentItemID() {
		return studentItemID;
	}

	public void setStudentItemID(long studentItemID) {
		this.studentItemID = studentItemID;
	}

	public int getStudentItemType() {
		return studentItemType;
	}

	public void setStudentItemType(int studentItemType) {
		this.studentItemType = studentItemType;
	}

	public String getStudentItemName() {
		return studentItemName;
	}

	public void setStudentItemName(String studentItemName) {
		this.studentItemName = studentItemName;
	}

	public String getStudentItemPath() {
		return studentItemPath;
	}

	public void setStudentItemPath(String studentItemPath) {
		this.studentItemPath = studentItemPath;
	}

}