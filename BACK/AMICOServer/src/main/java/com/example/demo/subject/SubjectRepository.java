package com.example.demo.subject;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	Subject findByInternalName(String internalName);
	
	Subject findBySubjectID(long subjectID);
	
	Page<Subject> findByCourse(Course course, Pageable page);
}
