package com.english.service;

import java.util.List;

import com.english.dto.DetailVocabularyDto;
import com.english.dto.VocabularyLessonDto;

public interface DetailVocabularyService {

    DetailVocabularyDto save(DetailVocabularyDto detailVocabulary);

    void delete(Integer id);

    List<DetailVocabularyDto> getByExerciseVocabulary(VocabularyLessonDto exerciseVocabulary);

}
