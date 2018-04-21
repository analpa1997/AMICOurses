package com.example.demo.studyItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.course.Course;
import com.example.demo.course.CourseRepository;
import com.example.demo.practices.Practices;
import com.example.demo.practices.PracticesRepository;
import com.example.demo.studyItem.StudyItem;
import com.example.demo.studyItem.StudyItemRepository;
import com.example.demo.subject.Subject;
import com.example.demo.subject.SubjectRepository;
import com.example.demo.user.User;

@Service
public class StudyItemService {

	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private PracticesRepository practiceRepository;

	public Path getStudyItemFile(Long courseID, Long subjectID, Long studyItemID) {
		StudyItem studyItem = studyItemRepository.findOne(studyItemID);

		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/documents/" + courseID + "/" + subjectID + "/studyItems/");
		String extension = studyItem.getExtension();
		if (extension == null) {
			String[] fileOriginal = studyItem.getOriginalName().split("[.]");
			extension = fileOriginal[fileOriginal.length - 1];
		}
		Path filePath = FILES_FOLDER.resolve("studyItem-" + studyItem.getStudyItemID() + "." + extension);
		return filePath;
	}

	public StudyItem createStudyItem(MultipartFile file, Subject subject, Integer module, String itemType,
			String itemName) throws IOException {

		StudyItem studyItem;

		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/documents/" + subject.getCourse().getCourseID() + "/" + subject.getSubjectID() + "/studyItems/");
		if (!Files.exists(FILES_FOLDER)) {
			Files.createDirectories(FILES_FOLDER);
		}
		String[] fileOriginal = file.getOriginalFilename().split("[.]");
		String extension = fileOriginal[fileOriginal.length - 1];
		String type = extension;
		if (!itemType.isEmpty()) {
			type = itemType;
		}
			studyItem = new StudyItem(type, itemName, module, file.getOriginalFilename());
			studyItem = studyItemRepository.save(studyItem);
		if (module < 0) {
			studyItem.setPractice(true);
		}

		String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
		studyItem.setExtension(extension);
		studyItem.setFileName(fileName);

		File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
		file.transferTo(uploadedFile);

		studyItem.setSubject(subject);
		studyItem = studyItemRepository.save(studyItem);
		subject.getStudyItemsList().add(studyItem);
		subjectRepository.save(subject);

		return studyItem;
	}
	
	public StudyItem createStudyItem(Subject subject, StudyItem studyItem, boolean isPractice){

		studyItem.setPractice(isPractice);
		studyItem.setSubject(subject);
		studyItem.setType(studyItem.getIcon());
		studyItem = studyItemRepository.save(studyItem);
		
		subject.getStudyItemsList().add(studyItem);
		subjectRepository.save(subject);

		return studyItem;
	}
	
	

	public StudyItem modifyStudyItem(StudyItem studyItem, StudyItem newStudyItem) throws IOException {

		studyItem.copy(newStudyItem);
		studyItem = studyItemRepository.save(studyItem);
		return studyItem;
	}

	public StudyItem deleteStudyItem(StudyItem studyItem) {

		/* The subject part */
		studyItem.getSubject().getStudyItemsList().remove(studyItem);
		subjectRepository.save(studyItem.getSubject());
		studyItem.setSubject(null);

		/* The practices part */
		if (studyItem.isPractice()) {
			List toRemove = new ArrayList<>();
			for (Practices practice : studyItem.getPractices()) {
				toRemove.add(practice);
				practice.setStudyItem(null);
				practice.setOwner(null);
				practiceRepository.delete(practice);
			}
		}
		
		studyItem.setPractices(null);
		studyItemRepository.delete(studyItem);

		return studyItem;
	}
	
	public StudyItem modifyStudyItemFile(MultipartFile file, StudyItem studyItem) throws IllegalStateException, IOException {

		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/documents/" + studyItem.getSubject().getCourse().getCourseID() + "/" + studyItem.getSubject().getSubjectID() + "/studyItems/");
		if (!Files.exists(FILES_FOLDER)) {
			Files.createDirectories(FILES_FOLDER);
		}
		String[] fileOriginal = file.getOriginalFilename().split("[.]");
		String extension = fileOriginal[fileOriginal.length - 1];
		studyItem.setOriginalName(file.getOriginalFilename());
		
		String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
		studyItem.setExtension(extension);
		studyItem.setFileName(fileName);

		File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
		file.transferTo(uploadedFile);
		
		studyItem = studyItemRepository.save(studyItem);
		
		return studyItem;
	}
	
	

}