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
	private int studentItemID;

	private int studentItemType;

	private String studentItemName;

	private int studentItemContent;

	public int getStudentItemID() {
		return studentItemID;
	}

	public void setStudentItemID(int studentItemID) {
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

	public int getStudentItemContent() {
		return studentItemContent;
	}

	public void setStudentItemContent(int studentItemContent) {
		this.studentItemContent = studentItemContent;
	}

}