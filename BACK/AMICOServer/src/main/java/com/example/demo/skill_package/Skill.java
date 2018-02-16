package com.example.demo.skill_package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.course_package.Course;

@Entity
@Table(name = "Skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long skillID;

	private String skillName;

	private String skillDescription;

	@ManyToOne
	private Course course;

	/* Constructors */
	public Skill () { }
	
	/* Methods */
	public long getSkillID() {
		return skillID;
	}

	public void setSkillID(long skillID) {
		this.skillID = skillID;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillDescription() {
		return skillDescription;
	}

	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}