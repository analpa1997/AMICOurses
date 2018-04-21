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

	public interface BasicPractice {
	}

	public interface DetailedPractice {
	}

	@JsonView(BasicPractice.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long practiceID;

	@JsonView(BasicPractice.class)
	private String practiceName;

	@JsonView(BasicPractice.class)
	private double calification;

	@JsonView(DetailedPractice.class)
	@ManyToOne
	private StudyItem studyItem;

	@JsonView(DetailedPractice.class)
	@ManyToOne
	private User owner;

	@JsonView(BasicPractice.class)
	private String originalName;

	@JsonView(BasicPractice.class)
	private boolean presented;

	@JsonView(BasicPractice.class)
	private boolean corrected;

	/* Constructors */

	public Practices() {
	}

	public Practices(String practiceName, String originalName) {
		this.practiceName = practiceName;
		this.originalName = originalName;
		this.calification = 0d;
		this.presented = false;
		this.setCorrected(false);
	}
	
	public Practices (String practiceName) {
		this.practiceName = practiceName;
		this.originalName = "";
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

	@Override
	public boolean equals(Object obj2) {
		boolean sameObj = false;
		if (obj2 != null && obj2 instanceof Practices) {
			sameObj = this.practiceID == ((Practices) obj2).practiceID;
		}
		return sameObj;
	}

}