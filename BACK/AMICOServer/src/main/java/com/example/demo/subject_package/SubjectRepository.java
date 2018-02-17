package com.example.demo.subject_package;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	@Query("select s from Subjects s where s.course.courseID = ?1")
	static
	List<Subject> findByCourseID(long courseID) {
		// TODO Auto-generated method stub
		return null;
	}

}
