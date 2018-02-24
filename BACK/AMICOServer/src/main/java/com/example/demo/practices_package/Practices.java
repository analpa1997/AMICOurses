package com.example.demo.practices_package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.studyItem_package.StudyItem;
import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.User;

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
	
	@ManyToOne
	private StudyItem studyItem;
	
	@ManyToOne
	private User owner;
	
	private String originalName;
	
	
	/* Constructors */
	
	public Practices () {}
	
	public Practices(String practiceName, String originalName) {
		this.practiceName = practiceName;
		this.originalName = originalName;
		this.calification = 0d;
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
		return studyItem.getSubject();
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

	public StudyItem getStudyItem() {
		return studyItem;
	}

	public void setStudyItem(StudyItem studyItem) {
		this.studyItem = studyItem;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	
	

}