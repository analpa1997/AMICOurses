package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userID;

	protected String userFirstName;

	protected String userLastName_1;

	protected String userLastName_2;

	protected String userAddress;

	protected int userMobile;

	protected String userMail;

	protected String login;

	protected String password;

	protected String urlProfileImage;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
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

	public String getUrlProfileImage() {
		return urlProfileImage;
	}

	public void setUrlProfileImage(String urlProfileImage) {
		this.urlProfileImage = urlProfileImage;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userFirstName=" + userFirstName + ", userLastName_1=" + userLastName_1
				+ ", userLastName_2=" + userLastName_2 + ", userAddress=" + userAddress + ", userMobile=" + userMobile
				+ ", userMail=" + userMail + ", login=" + login + ", password=" + password + ", urlProfileImage="
				+ urlProfileImage + "]";
	}

}