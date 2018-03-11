package com.example.demo.subject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void addModule(Subject subject) {
		subject.addModule();
		subjectRepository.save(subject);
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
	public List<StudyItem> getStudyItems(Subject subject) {

		List<StudyItem> studyItems = new ArrayList<>();

		int modules = subject.getNumberModules();

		for (int i = 1; i <= modules; i++) {
			studyItems.addAll(this.getStudyItems(subject, i));
		}
		return studyItems;
	}

	/* Retrieves only the studyItems from a module */
	public List<StudyItem> getStudyItems(Subject subject, int module) {

		List<StudyItem> studyItems = new ArrayList<>();

		for (StudyItem studyItem : subject.getStudyItemsList()) {
			if (!studyItem.isPractice() && studyItem.getModule() == module) {
				studyItems.add(studyItem);
			}
		}
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
			if (!studyItem.isPractice() && studyItem.getStudyItemID() == studyItemID) {
				return studyItem;
			}
		}
		return null;
	}

}