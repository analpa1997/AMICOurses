package com.example.demo.entities;

import java.util.List;
import java.util.Stack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long skillID;

	private String skillName;

	private Stack<Skill> skillDescription;

	private List<Course> course;

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

	public Stack getSkillDescription() {
		return skillDescription;
	}

	public void setSkillDescription(Stack skillDescription) {
		this.skillDescription = skillDescription;
	}

	public List getCourse() {
		return course;
	}

	public void setCourse(List course) {
		this.course = course;
	}

}