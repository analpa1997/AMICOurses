package com.example.demo.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.course.Course;
import com.example.demo.subject.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "Users")
public class User {

	/* Jackson Interfaces */
	public interface BasicUser {
	}

	public interface ExtendedUser {
	}

	public interface CourseInformation {
	}

	public interface TeachingInformation {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(BasicUser.class)
	private long userID;

	@JsonView(BasicUser.class)
	private String username;

	// TO DO: Convert the password using a hash function
	@JsonIgnore
	private String password;

	@JsonView(BasicUser.class)
	private String userMail;

	@JsonView(BasicUser.class)
	private String userFirstName;

	@JsonView(BasicUser.class)
	private String userLastName;

	@JsonView(ExtendedUser.class)
	private String userAddress;

	@JsonView(ExtendedUser.class)
	private String city;

	@JsonView(ExtendedUser.class)
	private String country;

	@JsonView(ExtendedUser.class)
	private int phoneNumber;

	@JsonView(ExtendedUser.class)
	@Column(length = Short.MAX_VALUE)
	private String interests;

	@JsonView(ExtendedUser.class)
	private String urlProfileImage;

	@JsonView(CourseInformation.class)
	@ManyToMany
	private List<Course> inscribedCourses = new ArrayList<>();

	private boolean isStudent;

	private String internalName;

	@JsonView(TeachingInformation.class)
	@ManyToMany(mappedBy = "teachers")
	private List<Subject> teaching = new ArrayList<>();

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	/*
	 * Constructors
	 *
	 * /*Empty constructor for the DB
	 */
	public User() {
	}

	/* Minimum fields constructor */
	public User(String username, String password, String userMail, boolean isStudent) {
		super();
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.userMail = userMail;
		this.isStudent = isStudent;
		urlProfileImage = "null";
		internalName = username.replaceAll(" ", "-").toLowerCase();

		userFirstName = "";
		userLastName = "";
		userAddress = "";
		city = "";
		country = "";
		phoneNumber = 00000000;
		interests = "";
		roles = new ArrayList<>(Arrays.asList("ROLE_USER"));
	}

	/* Methods */

	@Override
	public boolean equals(Object obj2) {

		boolean sameObj = false;

		if (obj2 != null && obj2 instanceof User)
			sameObj = userID == ((User) obj2).userID;
		return sameObj;
	}

	public String getCity() {
		return city;
	}

	@JsonView(ExtendedUser.class)
	public List<Course> getCompletedCourses() {
		List<Course> completedCourses = new ArrayList<>();
		for (Course course : inscribedCourses)
			if (course.isCompleted())
				completedCourses.add(course);
		return completedCourses;
	}

	public String getCountry() {
		return country;
	}

	@JsonView(ExtendedUser.class)
	public List<Course> getCurrentCourses() {
		List<Course> notCompletedCourses = new ArrayList<>();
		for (Course course : inscribedCourses)
			if (!course.isCompleted())
				notCompletedCourses.add(course);
		return notCompletedCourses;
	}

	public List<Course> getInscribedCourses() {
		return inscribedCourses;
	}

	public String getInterests() {
		return interests;
	}

	public String getInternalName() {
		return internalName;
	}

	public String getPassword() {
		return password;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public List<String> getRoles() {
		return roles;
	}

	public List<Subject> getTeaching() {
		return teaching;
	}

	public String getUrlProfileImage() {
		return urlProfileImage;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public long getUserID() {
		return userID;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public String getUserMail() {
		return userMail;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin() {
		return getRoles().contains("ROLE_ADMIN");
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setInscribedCourses(List<Course> inscribedCourses) {
		this.inscribedCourses = inscribedCourses;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public void setTeaching(List<Subject> teaching) {
		this.teaching = teaching;
	}

	public void setUrlProfileImage(String urlProfileImage) {
		this.urlProfileImage = urlProfileImage;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public void setUsername(String username) {
		this.username = username;
		internalName = this.username.replaceAll(" ", "-").toLowerCase();
	}

}