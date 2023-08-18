package com.english.repository;

import java.util.List;

import com.english.entities.VocabularyLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VocabularyLessonRepository extends JpaRepository<VocabularyLesson,Integer> {
	
	VocabularyLesson findById(int id);
	
	@Query("select vocab FROM VocabularyLesson vocab WHERE vocab.name LIKE CONCAT('%',:search,'%')")
	List<VocabularyLesson> searchVocab(@Param("search") String search);
}
