package com.example.demo.exam_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.User;

@Entity
@Table(name = "Exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long examID;

	private String name;

	private int mark;

	private Date date;
	
	@ManyToOne
	private Subject subject;

	/* Constructors */
	public Exam() { }
	
	/* Methods */

	public long getExamID() {
		return examID;
	}

	public void setExamID(long examID) {
		this.examID = examID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	

}