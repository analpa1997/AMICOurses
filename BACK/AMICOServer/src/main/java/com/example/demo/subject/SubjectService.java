package com.example.demo.subject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.exam.Exam;
import com.example.demo.exam.ExamRepository;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.studyItem.StudyItemService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Service
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private StudyItemService studyItemService;
	@Autowired
	private PracticesRepository practiceSubmissionRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private PracticesRepository practicesRepository;
	@Autowired
	private UserRepository userRepository;

	public void createSubject(Course c, Subject s) {
		if (c != null && s != null) {
			s.setCourse(c);
			s = subjectRepository.save(s);

			c.getSubjects().add(s);
			courseRepository.save(c);
		}
	}

	public void deleteSubject(Subject s, Course c) {
		for (Exam ex : s.getExams()) {
			examRepository.delete(ex);
		}
		for (StudyItem sT : s.getStudyItemsList()) {
			for (Practices prac : sT.getPractices()) {
				practicesRepository.delete(prac);
			}
			studyItemRepository.delete(sT);
		}
		c.getSubjects().remove(s);
		subjectRepository.delete(s);
	}

	public void addModule(Subject subject) {
		subject.addModule();
		subjectRepository.save(subject);
	}

	public void updateSubject(Course course, Subject updSubj, Subject s) {
		String name = s.getName();
		String description = s.getDescription();

		if (name != null) {
			updSubj.setName(name);
		}
		if (description != null) {
			updSubj.setDescription(description);
		}
		if (course != null) {
			updSubj.setCourse(course);
		}

		if (s.getTeachers() != null) {

			for (User teacher : s.getTeachers()) {
				if (!course.getInscribedUsers().contains(teacher)) {
					course.getInscribedUsers().add(teacher);
					teacher.getInscribedCourses().add(course);
					course = courseRepository.save(course);
					teacher = userRepository.save(teacher);
				}
			}

			updSubj.setTeachers(s.getTeachers());
		}
		subjectRepository.save(updSubj);
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

		Page<StudyItem> studyItems = studyItemRepository.findBySubjectAndModuleAndIsPractice(subject, module, false,
				pageReq);

		return studyItems;
	}

	public List<StudyItem> getPractices(Subject subject) {
		List<StudyItem> practices = new ArrayList<>();

		for (StudyItem studyItem : subject.getStudyItemsList()) {
			if (studyItem.isPractice()) {
				practices.add(studyItem);
			}
		}
		return practices;
	}

	public StudyItem getStudyItem(Subject subject, Long studyItemID) {

		for (StudyItem studyItem : subject.getStudyItemsList()) {
			if (studyItem.getStudyItemID() == studyItemID) {
				return studyItem;
			}
		}
		return null;
	}

}