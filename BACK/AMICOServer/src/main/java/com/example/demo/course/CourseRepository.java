package com.example.demo.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends JpaRepository<Course, Long>, PagingAndSortingRepository<Course, Long> {
	Page<Course> findByName(String name, Pageable page);

	Course findByInternalName(String internalName);
	
	Course findByName(String name);

	Page<Course> findByInternalNameContaining(String internalName, Pageable page);

	Page<Course> findByType(String typeCourse, Pageable page);
}
