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

	
	
	

}