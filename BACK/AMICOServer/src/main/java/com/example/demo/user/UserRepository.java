package com.example.demo.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserID(long id);

	User findByUsername(String name);

	User findByInternalName(String name);

	User findByUserMail(String userMail);

	List<User> findByIsStudent(boolean isStudent);

	Page<User> findByIsStudent(boolean isStudent, Pageable page);
}
