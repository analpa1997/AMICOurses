package com.example.demo.course_package;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
	Page<Course> findByName(String name, Pageable page);

	Course findByInternalName(String internalName);;
}
