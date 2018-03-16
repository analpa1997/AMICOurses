package com.example.demo.practices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.studyItem.StudyItem;
import com.example.demo.subject.Subject;
import com.example.demo.user.User;

public interface PracticesRepository extends JpaRepository<Practices, Long> {

	Page<Practices> findByStudyItemAndOwner(StudyItem studyItem, User owner, Pageable page);
	Page<Practices> findByStudyItem(StudyItem studyItem, Pageable page);
}
