package com.example.demo.user_package;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.demo.course_package.Course;
import com.example.demo.subject_package.Subject;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;
	
	private String username;
	
	//TO DO: Convert the password using a hash function 
	private String password;
	
	private String userMail;

	private String userFirstName;

	private String userLastName;

	private String userAddress;
	
	private String city;
	
	private String country;

	private int phoneNumber;
	
	@Column(length = Short.MAX_VALUE)
	private String interests;

	private String urlProfileImage;
	@ManyToMany(mappedBy="inscribedUsers")
	private List<Course> inscribedCourses = new ArrayList<>();
	
	private boolean isStudent;
	
	/* The rights the user will have*/
	private String role;
	
	private String internalName;
	
	@ManyToMany (mappedBy="teachers")
	private List <Subject> teaching = new ArrayList <> ();
	
	/* Constructors 

	/*Empty constructor for the DB */
	public User () { }
	
	/*Minimum fields constructor*/
	public User(String username, String password, String userMail, boolean isStudent) {
		super();
		this.username = username;
		this.password = password;
		this.userMail = userMail;
		this.isStudent = isStudent;
		this.urlProfileImage = "../img/users/default/default.png";
		this.internalName = username.replaceAll(" ", "-").toLowerCase();
	}
	
	/* Methods */
	
	
	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUrlProfileImage() {
		return urlProfileImage;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public void setUrlProfileImage(String urlProfileImage) {
		this.urlProfileImage = "../img/users/" + this.internalName + "/" + urlProfileImage.replaceAll(" ", "-");
	}
	
	public List<Course> getInscribedCourses() {
		List<Course> notCompletedCourses = new ArrayList <>();
		for (Course course : inscribedCourses) {
			if (!course.isCompleted()) {
				notCompletedCourses.add(course);
			}
		}
		return notCompletedCourses;
	}

	public void setInscribedCourses(List<Course> inscribedCourses) {
		this.inscribedCourses = inscribedCourses;
	}

	public List<Course> getCompletedCourses() {
		List<Course> completedCourses = new ArrayList<> ();
		for (Course course : inscribedCourses) {
			if (course.isCompleted()) {
				completedCourses.add(course);
			}
		}
		return completedCourses;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public List<Subject> getTeaching() {
		return teaching;
	}

	public void setTeaching(List<Subject> teaching) {
		this.teaching = teaching;
	}

}