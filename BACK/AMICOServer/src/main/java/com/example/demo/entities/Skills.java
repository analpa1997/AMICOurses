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
public class Skills {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int skillID;

	private String skillName;

	private Stack skillDescription;

	private List course;

	public int getSkillID() {
		return skillID;
	}

	public void setSkillID(int skillID) {
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