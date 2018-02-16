package com.example.demo.message_package;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.user_package.User;

@Entity
@Table(name = "Messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long messageID;

	private String topic;

	private String text;

	@OneToOne
	private User author;
	
	@OneToMany
	private List <User> receivers;

	/* Constructors */
	public Message () {}
	
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
	

}