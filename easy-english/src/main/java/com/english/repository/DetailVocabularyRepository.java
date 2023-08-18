package com.english.repository;

import java.util.List;

import com.english.entities.DetailVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailVocabularyRepository extends JpaRepository<DetailVocabulary,Integer>{
    @Query("select entity from DetailVocabulary entity where entity.vocabularyLesson.id = ?1")
	List<DetailVocabulary> findByExerciseVocabulary(Integer exerciseId);
	
}
