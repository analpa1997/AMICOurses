package com.example.demo.studyItem_package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.subject_package.Subject;

@Entity
@Table(name = "StudyItems")
public class StudyItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studyItemID;

	private int type;

	private String name;

	private String path;
	
	private String fileName;
	
	@ManyToOne
	private Subject subject;
	
	private int module;
	
	private String internalName;

	/* Constructors */
	public StudyItem () {}
	
	
	
	public StudyItem(int type, String name, int module, String fileName) {
		super();
		this.type = type;
		this.name = name;
		this.module = module;
		this.fileName = fileName;
		this.internalName = fileName.replaceAll(" ", "-");
	}



	/* Methods */

	public long getStudyItemID() {
		return studyItemID;
	}

	public void setStudyItemID(long studyItemID) {
		this.studyItemID = studyItemID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
		this.path = "../files/" + subject.getCourse().getInternalName() + "/" + internalName;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getInternalName() {
		return internalName;
	} 
	

	

}