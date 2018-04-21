package com.example.demo.course;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.skill.Skill;
import com.example.demo.subject.Subject;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Courses")
public class Course {

	public interface BasicCourse {
	}

	public interface UserInformation {
	}
	
	public interface ExtendedCourse {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicCourse.class)
	private long courseID;
	@JsonView(BasicCourse.class)
	private String name;
	@JsonView(BasicCourse.class)
	private String internalName;
	@JsonView(BasicCourse.class)
	private String courseLanguage;
	@JsonView(BasicCourse.class)
	private String type;
	@JsonIgnore
	private Date startDate;
	@JsonIgnore
	private Date endDate;
	@JsonView(BasicCourse.class)
	private String startDateString;
	@JsonView(BasicCourse.class)
	private String endDateString;
	@JsonView(BasicCourse.class)
	private int numberOfUsers;
	@JsonView(BasicCourse.class)
	@Column(length = 1500)
	private String courseDescription;
	@JsonView(BasicCourse.class)
	private String originalName;
	@JsonIgnore
	private boolean isCompleted;

	@JsonView(ExtendedCourse.class)
	@ManyToMany(mappedBy = "inscribedCourses", fetch = FetchType.LAZY)
	private List<User> inscribedUsers = new ArrayList<>();
	@JsonView(ExtendedCourse.class)
	@OneToMany(mappedBy = "course")
	private List<Subject> subjects = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private List<Skill> skills = new ArrayList<>();

	/* Constructors */
	public Course() {
	}

	public Course(String name, String description) {
		super();
		this.name = name;
		courseDescription = description;
	}

	public Course(String name, String courseLanguage, Date startDate, Date endDate, String courseDescription,
			String type, String urlImage) {
		super();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		this.name = name;
		internalName = this.name.replaceAll(" ", "-").toLowerCase();
		this.courseLanguage = courseLanguage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseDescription = courseDescription;
		this.type = type.replaceAll(" ", "-").toLowerCase();
		originalName = "../img/courses/" + internalName + "/" + urlImage;
		isCompleted = false;
		startDateString = dateFormat.format(this.startDate);
		endDateString = dateFormat.format(this.endDate);
	}

	public Course(String name, String courseLanguage, String courseDescription, String type, String urlImage) {
		super();
		this.name = name;
		internalName = this.name.replaceAll(" ", "-").toLowerCase();
		this.courseLanguage = courseLanguage;
		this.courseDescription = courseDescription;
		this.type = type.replaceAll(" ", "-").toLowerCase();
		originalName = "../img/courses/" + internalName + "/" + urlImage;
		isCompleted = false;

		endDate = Date.valueOf(LocalDate.now());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Random random = new Random();
		LocalDate start = LocalDate.of(2018, Month.MARCH, 1);
		LocalDate end = LocalDate.of(2025, Month.DECEMBER, 31);
		long days = ChronoUnit.DAYS.between(start, end);
		long months = ChronoUnit.MONTHS.between(start, end);
		long years = ChronoUnit.YEARS.between(start, end);
		LocalDate randomDate = start.plusDays(random.nextInt((int) days + 1));
		randomDate = start.plusMonths(random.nextInt((int) months + 1));
		randomDate = start.plusYears(random.nextInt((int) years + 1));
		Date date = Date.valueOf(randomDate);
		startDate = date;

		while (endDate.compareTo(startDate) < 0) {
			random = new Random();
			LocalDate randomDate2 = start.plusDays(random.nextInt((int) days + 1));
			randomDate2 = start.plusMonths(random.nextInt((int) months + 1));
			randomDate2 = start.plusYears(random.nextInt((int) years + 1));
			Date date2 = Date.valueOf(randomDate2);
			endDate = date2;
		}

		startDateString = dateFormat.format(startDate);
		endDateString = dateFormat.format(endDate);
	}

	/* Methods */

	public String getCourseDescription() {
		return courseDescription;
	}

	public long getCourseID() {
		return courseID;
	}

	public String getCourseLanguage() {
		return courseLanguage;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public List<User> getInscribedUsers() {
		return inscribedUsers;
	}

	public String getInternalName() {
		return internalName;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public String getOriginalName() {
		return originalName;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public String getType() {
		return type;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public void setCourseID(long courseID) {
		this.courseID = courseID;
	}

	public void setCourseLanguage(String courseLanguage) {
		this.courseLanguage = courseLanguage;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public void setInscribedUsers(List<User> inscribedUsers) {
		this.inscribedUsers = inscribedUsers;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public void setName(String name) {
		this.name = name;
		internalName = name.replaceAll(" ", "-").toLowerCase();
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public void setOriginalName(String urlImage) {
		originalName = urlImage;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public void setType(String type) {
		this.type = type;
	}

}