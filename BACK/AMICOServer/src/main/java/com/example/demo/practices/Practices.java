package com.example.demo.practices;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.studyItem.StudyItem;
import com.example.demo.subject.Subject;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Practices")
public class Practices {
	
	public interface Basic {}
	public interface Detailed {}

	@JsonView(Basic.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long practiceID;

	@JsonView(Basic.class)
	private String practiceName;

	@JsonView(Basic.class)
	private double calification;

	@JsonView(Basic.class)
	private String fileName;
	
	@JsonView(Basic.class)
	private String internalName;
	
	@JsonView(Detailed.class)
	@ManyToOne
	private StudyItem studyItem;
	
	@JsonView(Detailed.class)
	@ManyToOne
	private User owner;
	
	@JsonView(Basic.class)
	private String originalName;
	
	@JsonView(Basic.class)
	private boolean presented;
	
	@JsonView(Basic.class)
	private boolean corrected;
	
	
	/* Constructors */
	
	public Practices () {}
	
	public Practices(String practiceName, String originalName) {
		this.practiceName = practiceName;
		this.originalName = originalName;
		this.calification = 0d;
		this.presented = false;
		this.setCorrected(false);
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

	public boolean isPresented() {
		return presented;
	}

	public void setPresented(boolean presented) {
		this.presented = presented;
	}

	public boolean isCorrected() {
		return corrected;
	}

	public void setCorrected(boolean corriged) {
		this.corrected = corriged;
	}

	
	

}