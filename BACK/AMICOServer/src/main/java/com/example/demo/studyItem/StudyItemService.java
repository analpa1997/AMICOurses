package com.example.demo.studyItem;

import java.nio.file.Path;
import java.nio.file.Paths;
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
public class StudyItemService {
	
	@Autowired
	private StudyItemRepository studyItemRepository;

	
	public Path getStudyItemFile(Long courseID, Long subjectID, Long studyItemID) {
		StudyItem studyItem = studyItemRepository.findOne(studyItemID);
		
		
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files/documents/"
				+ courseID + "/" + subjectID + "/studyItems/");
		String extension = studyItem.getExtension();
		if (extension == null) {
			String[] fileOriginal = studyItem.getOriginalName().split("[.]");
			extension = fileOriginal[fileOriginal.length - 1];
		}
		Path filePath = FILES_FOLDER
				.resolve("studyItem-" + studyItem.getStudyItemID() + "." + extension);
		return filePath;
	}

}