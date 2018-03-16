package com.example.demo.studyItem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.practices.Practices;
import com.example.demo.subject.Subject;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "StudyItems")
public class StudyItem {

	/* Jackson Interfaces */
	public interface BasicStudyItem {
	}

	public interface Practice {
	}

	public interface SubjectOrigin {
	}

	@JsonView(BasicStudyItem.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studyItemID;

	@JsonView(BasicStudyItem.class)
	private String type;

	@JsonView(BasicStudyItem.class)
	private String name;

	@JsonView(BasicStudyItem.class)
	private String fileName;

	@JsonView(SubjectOrigin.class)
	@ManyToOne
	private Subject subject;

	@JsonView(BasicStudyItem.class)
	private Integer module;

	@JsonView(BasicStudyItem.class)
	private String originalName;

	@JsonView(BasicStudyItem.class)
	private String extension;

	@JsonView(BasicStudyItem.class)
	private String icon;

	@JsonView(BasicStudyItem.class)
	private boolean isPractice;

	@JsonView(Practice.class)
	@OneToMany(mappedBy = "studyItem")
	private List<Practices> practices = new ArrayList<>();

	/* Constructors */
	public StudyItem() {
	}

	public StudyItem(String type, String name, Integer module, String filename, String originalName) {
		setIcon(type);
		if (!type.isEmpty()) {
			type = "-" + type;
		}
		this.type = "file" + type;
		this.name = name;
		this.module = module;
		this.fileName = filename;
		this.originalName = originalName;
		this.isPractice = false;
	}

	public StudyItem(String type, String name, Integer module, String originalName) {
		this(type, name, module, "", originalName);
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
		if (!type.isEmpty()) {
			type = "-" + type;
		}
		this.type = "file" + type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isPractice() {
		return isPractice;
	}

	public void setPractice(boolean isPractice) {
		this.isPractice = isPractice;
	}

	public List<Practices> getPractices() {
		return practices;
	}

	public void setPractices(List<Practices> practices) {
		this.practices = practices;
	}

	public void copy(StudyItem origin) {

		if (origin.name != null && !origin.name.isEmpty()) {
			this.name = origin.name;
		}
		if (origin.fileName != null && !origin.fileName.isEmpty()) {
			this.fileName = origin.fileName;
		}
		if (origin.module != null) {
			this.module = origin.module;
		}
		if (origin.originalName != null && !origin.originalName.isEmpty()) {
			this.originalName = origin.originalName;
		}
		if (origin.extension != null && !origin.extension.isEmpty()) {
			this.extension = origin.extension;
		}
		if (origin.icon != null && !origin.icon.isEmpty()) {
			this.icon = origin.icon;
			this.type = "file-" + icon;
		}
		if (origin.practices != null && !origin.practices.isEmpty()) {
			this.practices = origin.practices;
		}
	}

	@Override
	public boolean equals(Object obj2) {
		boolean sameObj = false;
		if (obj2 != null && obj2 instanceof StudyItem)
			sameObj = this.studyItemID == ((StudyItem) obj2).studyItemID;
		return sameObj;
	}

}