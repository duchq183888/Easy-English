package com.english.service;

import java.util.List;

import com.english.dto.VocabularyLessonDto;
import org.springframework.data.domain.Page;


public interface ExerciseVocabularyService {

	List<VocabularyLessonDto> findAll();

	VocabularyLessonDto save(VocabularyLessonDto exerciseVocabulary);

	void delete(int id);

	Page<VocabularyLessonDto> getAll(int page, int limit);

	VocabularyLessonDto getById(Integer id);

	VocabularyLessonDto getById(int id);

	List<VocabularyLessonDto> search(String search);

}
