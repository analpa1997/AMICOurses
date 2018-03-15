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

	public StudyItem createStudyItem(MultipartFile file, Subject subject, Integer module, String itemType, String itemName) throws IOException {
		

		StudyItem studyItem = createStudyItemFile(file, subject, module, itemType, itemName);

		studyItem.setSubject(subject);
		studyItem = studyItemRepository.save(studyItem);
		subject.getStudyItemsList().add(studyItem);
		studyItem = studyItemRepository.save(studyItem);
		subjectRepository.save(subject);
		
		return studyItem;
	}
	
	
	public StudyItem modifyStudyItem (MultipartFile file, Subject subject, String itemType, String itemName, StudyItem studyItem) throws IOException {
		
		if (itemName == null || itemName.isEmpty()) {
			itemName = studyItem.getName();
		}
		
		if (itemType == null || itemType.isEmpty()) {
			itemType = studyItem.getType();
		}
		if (file != null && !file.isEmpty()) {
			studyItem.copy(createStudyItemFile(file, subject, studyItem.getModule(), itemType, itemName));
		} else {
			studyItem.setType(itemType);
			studyItem.setName(itemName);
		}
		
		studyItemRepository.save(studyItem);
		return studyItem;
	}
	
	private StudyItem createStudyItemFile (MultipartFile file, Subject subject, Integer module, String itemType, String itemName) throws IOException {
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
			if (module < 0) {
				studyItem.setPractice(true);
			}
	
			String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
			studyItem.setExtension(extension);
			studyItem.setFileName(fileName);
			
			File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
			file.transferTo(uploadedFile);
		
		return studyItem;
	}
	
}