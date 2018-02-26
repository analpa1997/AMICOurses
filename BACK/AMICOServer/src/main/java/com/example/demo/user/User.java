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
	@ManyToMany
	private List<Course> inscribedCourses = new ArrayList<>();
	
	private boolean isStudent;
	
	private String internalName;
	
	@ManyToMany (mappedBy="teachers")
	private List <Subject> teaching = new ArrayList <> ();

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	/* Constructors 

	/*Empty constructor for the DB */
	public User () { }
	
	/*Minimum fields constructor*/
	public User(String username, String password, String userMail, boolean isStudent) {
		super();
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);;
		this.userMail = userMail;
		this.isStudent = isStudent;
		this.urlProfileImage = "null";
		this.internalName = username.replaceAll(" ", "-").toLowerCase();
		
		this.userFirstName = "";
		this.userLastName = "";
		this.userAddress = "";
		this.city = "";
		this.country = "";
		this.phoneNumber = 00000000;
		this.interests = "";
		this.roles = new ArrayList<>(Arrays.asList("ROLE_USER"));
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
		this.internalName = this.username.replaceAll(" ", "-").toLowerCase();
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
		this.urlProfileImage = urlProfileImage;
	}
	
	public List<Course> getInscribedCourses() {
		return inscribedCourses;
	}
	
	public List<Course> getCurrentCourses() {
		List<Course> notCompletedCourses = new ArrayList <>();
		for (Course course : this.inscribedCourses) {
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals (Object obj2) {
		
		boolean sameObj = false;

        if (obj2 != null && obj2 instanceof User)
        {
        	sameObj = (this.userID == ((User) obj2).userID);
        }
		return sameObj;
	}
	
	public boolean isAdmin () {
		return this.getRoles().contains("ROLE_ADMIN");
	}

}