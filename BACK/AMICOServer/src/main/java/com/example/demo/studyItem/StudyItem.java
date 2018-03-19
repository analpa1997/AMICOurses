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

	public StudyItem(String type, String name) {
		this(type, name, 0, "", "");
	}

	public StudyItem(String type, String name, Integer module, String originalName) {
		this(type, name, module, "", originalName);
	}

	public StudyItem(String type, String name, Integer module, String filename, String originalName) {
		if (type == null)
			type = "";
		setIcon(type);
		if (!type.isEmpty())
			type = "-" + type;
		this.type = "file" + type;
		this.name = name;
		this.module = module;
		fileName = filename;
		this.originalName = originalName;
		isPractice = false;

	}

	/* Methods */

	public void copy(StudyItem origin) {

		if (origin.name != null && !origin.name.isEmpty())
			name = origin.name;
		if (origin.fileName != null && !origin.fileName.isEmpty())
			fileName = origin.fileName;
		if (origin.module != null)
			module = origin.module;
		if (origin.originalName != null && !origin.originalName.isEmpty())
			originalName = origin.originalName;
		if (origin.extension != null && !origin.extension.isEmpty())
			extension = origin.extension;
		if (origin.icon != null && !origin.icon.isEmpty()) {
			icon = origin.icon;
			type = "file-" + icon;
		}
		if (origin.practices != null && !origin.practices.isEmpty())
			practices = origin.practices;
	}

	@Override
	public boolean equals(Object obj2) {
		boolean sameObj = false;
		if (obj2 != null && obj2 instanceof StudyItem)
			sameObj = studyItemID == ((StudyItem) obj2).studyItemID;
		return sameObj;
	}

	public String getExtension() {
		return extension;
	}

	public String getFileName() {
		return fileName;
	}

	public String getIcon() {
		return icon;
	}

	public int getModule() {
		return module;
	}

	public String getName() {
		return name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public List<Practices> getPractices() {
		return practices;
	}

	public long getStudyItemID() {
		return studyItemID;
	}

	public Subject getSubject() {
		return subject;
	}

	public String getType() {
		return type;
	}

	public boolean isPractice() {
		return isPractice;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public void setPractice(boolean isPractice) {
		this.isPractice = isPractice;
	}

	public void setPractices(List<Practices> practices) {
		this.practices = practices;
	}

	public void setStudyItemID(long studyItemID) {
		this.studyItemID = studyItemID;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void setType(String type) {
		if (!type.isEmpty())
			type = "-" + type;
		this.type = "file" + type;
	}

}