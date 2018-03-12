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
import com.example.demo.user.User;

@Service
public class StudyItemService {

	@Autowired
	private StudyItemRepository studyItemRepository;

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
		StudyItem studyItem = new StudyItem(type, itemName, module, file.getOriginalFilename());
		if (module < 0) {
			studyItem.setPractice(true);
		}

		studyItemRepository.save(studyItem);

		String fileName = "studyItem-" + studyItem.getStudyItemID() + "." + extension;
		studyItem.setExtension(extension);
		studyItem.setFileName(fileName);

		studyItem.setSubject(subject);
		subject.getStudyItemsList().add(studyItem);
		File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
		file.transferTo(uploadedFile);

		Long id = studyItemRepository.count();
		studyItemRepository.save(studyItem);
		// subjectRepository.save(subject);
		
		return studyItemRepository.findOne(id);
	}

}