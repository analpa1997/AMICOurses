package com.example.demo.skill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.course.Course;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Skills")
public class Skill {

	public interface BasicSkill {

	}

	public interface CourseSkill {

	}

	@JsonView(BasicSkill.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long skillID;

	@JsonView(BasicSkill.class)
	private String skillName;

	@JsonView(BasicSkill.class)
	private String skillDescription;

	@JsonView(CourseSkill.class)
	@ManyToOne
	private Course course;

	private String internalName;

	/* Constructors */
	public Skill() {
	}

	public Skill(String name) {
		super();
		skillName = name;
		internalName = name.replaceAll(" ", "-");
	}

	public Skill(String skillName, String skillDescription) {
		super();
		this.skillName = skillName;
		this.skillDescription = skillDescription;
	}

	public Course getCourse() {
		return course;
	}

	public String getSkillDescription() {
		return skillDescription;
	}

	/* Methods */
	public long getSkillID() {
		return skillID;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}

	public void setSkillID(long skillID) {
		this.skillID = skillID;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}