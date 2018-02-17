package com.example.demo.message_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.subject_package.Subject;
import com.example.demo.user_package.User;

@Entity
@Table(name = "Messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long messageID;

	private String topic;

	private String text;
	
	private String folder;
	
	private String date;

	@OneToOne
	private User author;
	
	@OneToMany
	private List <User> receivers = new ArrayList<>();
	
	@ManyToOne
	private Subject subject;

	/* Constructors */
	public Message () {}
	
	
	public Message(String topic, String text) {
		this.topic = topic;
		this.text = text;
		this.folder = "received";
		date = new Date().toString();
	}

	/* Methods */

	public long getMessageID() {
		return messageID;
	}

	public void setMessageID(long messageID) {
		this.messageID = messageID;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public String getFolder() {
		return folder;
	}



	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getDate() {
		return date;
	}
	

}