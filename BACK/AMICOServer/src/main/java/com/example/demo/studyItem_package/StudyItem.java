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

	private String type;

	private String name;

	private String path;
	
	private String fileName;
	
	@ManyToOne
	private Subject subject;
	
	private int module;
	
	private String internalName;
	
	private String originalName;

	/* Constructors */
	public StudyItem () {}
	
	
	
	public StudyItem(String type, String name, int module, String fileName) {
		super();
		if (!type.isEmpty()) {
			type = "-" + type;
		}
		this.type = "file" + type;
		this.name = name;
		this.module = module;
		this.setFileName(fileName);
		this.internalName = fileName.replaceAll(" ", "-");
	}
	
	public StudyItem(String name, int module, String fileName) {
		this("", name, module, fileName);
	}
	
	public StudyItem (String type, String name, String originalName) {
		this(type, name, 0, "");
		this.originalName = originalName;
	}



	/* Methods */

	public long getStudyItemID() {
		return studyItemID;
	}

	public void setStudyItemID(long studyItemID) {
		this.studyItemID = studyItemID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getOriginalName() {
		return originalName;
	}



	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	} 
	

	

}