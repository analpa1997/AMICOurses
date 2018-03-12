package com.example.demo.studyItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.subject.Subject;

public interface StudyItemRepository extends JpaRepository<StudyItem, Long> {
	
	Page<StudyItem> findBySubjectAndIsPractice(Subject subject, Boolean isPractice, Pageable page);
	Page<StudyItem> findBySubjectAndModuleAndIsPractice(Subject subject, Integer module, Boolean isPractice, Pageable page);

}
