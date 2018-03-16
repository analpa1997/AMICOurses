package com.example.demo.practices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
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
public class PracticesService {

	@Autowired
	private StudyItemRepository studyItemRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private PracticesRepository practiceRepository;
	
	
	public Page<Practices> getSubmissions(User user, StudyItem studyItem, int page) {
		Page<Practices> response = null;
		
		if (user.isStudent()) {
			response = practiceRepository.findByStudyItemAndOwner(studyItem, user, new PageRequest(page, 10));
		} else {
			response = practiceRepository.findByStudyItem(studyItem, new PageRequest(page, 10));
		}
		return response;
	}


	public Path getPracticeFile(StudyItem studyItem, Practices practice) {
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/documents/" + studyItem.getSubject().getCourse().getCourseID() + "/" + studyItem.getSubject().getSubjectID()
						+ "/studyItems/practices/");

		String[] fileOriginal = studyItem.getOriginalName().split("[.]");
		String extension = fileOriginal[fileOriginal.length - 1];

		
		Path filePath = FILES_FOLDER
				.resolve("practice-" + practice.getPracticeID() + "." + extension);


		return filePath;
	}


	public Practices createSubmission(User user, StudyItem studyItem, Practices practice) {
			
		practice.setOwner(user);
		practice.setCalification(0);
		practice.setCorrected(false);
		practice.setPresented(true);
		
		practice = practiceRepository.save(practice);
		studyItem.getPractices().add(practice);
		studyItemRepository.save(studyItem);
		return practice;
	}


	public Practices createSubmissionFile(User user, StudyItem studyItem, Practices practice, MultipartFile file) throws IOException {
		
		Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"),
				"files/documents/" + studyItem.getSubject().getCourse().getCourseID() + "/" + studyItem.getSubject().getSubjectID()
						+ "/studyItems/practices/");
		if (!Files.exists(FILES_FOLDER)) {
			Files.createDirectories(FILES_FOLDER);
		}

		practice.setOriginalName(file.getOriginalFilename());
		practiceRepository.save(practice);

		String[] fileOriginal = studyItem.getOriginalName().split("[.]");
		String extension = fileOriginal[fileOriginal.length - 1];

		String fileName = "practice-" + practice.getPracticeID() + "." + extension;

		File uploadedFile = new File(FILES_FOLDER.toFile(), fileName);
		file.transferTo(uploadedFile);
		
		return practice;
	}




	
	
	

}