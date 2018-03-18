package com.example.demo.subject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.user.User;

@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	public void createSubject(Course c, Subject s) {
		if(c!=null && s!=null) {
			s.setCourse(c);
			subjectRepository.save(s);
			c.getSubjects().add(s);
		}
	}
	
	public void deleteSubject(Subject s, Course c) {
		int i = c.getSubjects().indexOf(s);
		c.getSubjects().remove(i);
		subjectRepository.delete(s);
	}
	
	public void addModule(Subject subject) {
		subject.addModule();
		subjectRepository.save(subject);
	}

	public void updateSubject(Course c, Subject updSubj, Subject s) {
		String name = s.getName();
		String description = s.getDescription();
		Course course = s.getCourse();
		
		if(name != null) {
			updSubj.setName(name);
		}
		if(description != null) {
			updSubj.setDescription(description);
		}
		if(course != null) {
			updSubj.setCourse(course);
		}
	}
	
	public void deleteModule(Subject subject, int module) {
		/* Get the studyItems of the module */
		List<StudyItem> studyItemsToRemove = new ArrayList<>();
		for (StudyItem studyItemAct : subject.getStudyItemsList()) {
			if (studyItemAct.getModule() == module) {
				studyItemsToRemove.add(studyItemAct);
				studyItemAct.setSubject(null);
			} else {
				if (studyItemAct.getModule() > module) {
					studyItemAct.setModule(studyItemAct.getModule() - 1);
				}
			}
		}
		/* Now remove the studyItems from the subject */
		subject.getStudyItemsList().removeAll(studyItemsToRemove);
		studyItemRepository.delete(studyItemsToRemove);
		subject.deleteModule();
		subjectRepository.save(subject);
	}

	public Subject checkForSubject(User user, String courseInternalName, String subjectInternalName) {
		Course course = null;

		course = courseRepository.findByInternalName(courseInternalName);
		if (course != null && course.getInscribedUsers().contains(user)) {
			for (Subject subjectAct : course.getSubjects()) {
				if (subjectAct.getInternalName().equals(subjectInternalName)) {
					return subjectAct;
				}
			}
		}
		return null;
	}

	/* Retrieves all the studyItems from all modules */
	public Page<StudyItem> getStudyItems(Subject subject, PageRequest pageReq) {

		Page<StudyItem> studyItems = studyItemRepository.findBySubjectAndIsPractice(subject, false, pageReq);
		
		return studyItems;
	}
	
	/* Retrieves all the practices */
	public Page<StudyItem> getPractices(Subject subject, PageRequest pageReq) {

		Page<StudyItem> studyItems = studyItemRepository.findBySubjectAndIsPractice(subject, true, pageReq);
		
		return studyItems;
	}

	/* Retrieves only the studyItems from a module */
	public Page<StudyItem> getStudyItems(Subject subject, int module, PageRequest pageReq) {

		Page<StudyItem> studyItems = studyItemRepository.findBySubjectAndModuleAndIsPractice(subject, module, false, pageReq);
		
		return studyItems;
	}
	
	public List <StudyItem> getPractices (Subject subject){
		List<StudyItem> practices = new ArrayList<>();

		for (StudyItem studyItem : subject.getStudyItemsList()) {
			if (studyItem.isPractice()) {
				practices.add(studyItem);
			}
		}
		return practices;
	}
	
	public StudyItem getStudyItem (Subject subject, Long studyItemID){

		for (StudyItem studyItem : subject.getStudyItemsList()) {
			if (studyItem.getStudyItemID() == studyItemID) {
				return studyItem;
			}
		}
		return null;
	}

}