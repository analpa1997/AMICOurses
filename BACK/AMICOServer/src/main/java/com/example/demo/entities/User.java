package com.example.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userID;

	protected String userFirstName;

	protected String userLastName_1;

	protected String userLastName_2;

	protected String userAddress;

	protected int userMobile;

	protected String userMail;

	protected String login;

	protected String password;

	@OneToMany
	private List studentMessagesList;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName_1() {
		return userLastName_1;
	}

	public void setUserLastName_1(String userLastName_1) {
		this.userLastName_1 = userLastName_1;
	}

	public String getUserLastName_2() {
		return userLastName_2;
	}

	public void setUserLastName_2(String userLastName_2) {
		this.userLastName_2 = userLastName_2;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public int getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(int userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List getStudentMessagesList() {
		return studentMessagesList;
	}

	public void setStudentMessagesList(List studentMessagesList) {
		this.studentMessagesList = studentMessagesList;
	}

}