package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByCourseName(String name);
}
