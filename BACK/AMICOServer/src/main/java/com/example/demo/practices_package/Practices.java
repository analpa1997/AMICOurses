package com.example.demo.practices_package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.subject_package.Subject;

@Entity
@Table(name = "Practices")
public class Practices {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long practiceID;

	private String practiceName;

	private double calification;

	private String fileName;
	
	private String internalName;
	
	private String path;
	
	@ManyToOne
	private Subject subject;
	
	/* Constructors */
	
	public Practices () {}
	
	public Practices(String practiceName, String fileName) {
		super();
		this.practiceName = practiceName;
		this.internalName = fileName.replaceAll(" ", "-");
	}



	/* Methods */

	public long getPracticeID() {
		return practiceID;
	}

	public void setPracticeID(long practiceID) {
		this.practiceID = practiceID;
	}

	public String getPracticeName() {
		return practiceName;
	}

	public void setPracticeName(String practiceName) {
		this.practiceName = practiceName;
	}

	public double getCalification() {
		return calification;
	}

	public void setCalification(double calification) {
		this.calification = calification;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
		this.path = "../files/" + subject.getCourse().getInternalName() + "/" + internalName;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}