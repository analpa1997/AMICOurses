package com.example.demo.subject_package;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	
	Subject findBySubjectID(long subjectID);
}
