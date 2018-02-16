package com.example.demo.user_package;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.course_package.Course;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByUsername(String name);
}
