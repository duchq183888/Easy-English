package com.english.service.impl;

import java.util.List;

import com.english.dto.DetailVocabularyDto;
import com.english.dto.VocabularyLessonDto;
import com.english.entities.DetailVocabulary;
import com.english.util.ModelMapperObject;
import org.springframework.stereotype.Service;

import com.english.repository.DetailVocabularyRepository;
import com.english.service.DetailVocabularyService;

@Service
public class DetailVocabularyImpl implements DetailVocabularyService {
    private final ModelMapperObject modelMapperObject;

    private final DetailVocabularyRepository detailVocabularyRepository;

    public DetailVocabularyImpl(ModelMapperObject modelMapperObject, DetailVocabularyRepository detailVocabularyRepository) {
        this.modelMapperObject = modelMapperObject;
        this.detailVocabularyRepository = detailVocabularyRepository;
    }

    @Override
    public DetailVocabularyDto save(DetailVocabularyDto detailVocabulary) {
        DetailVocabulary entity = (DetailVocabulary) modelMapperObject.mapToObject(detailVocabulary,DetailVocabulary.class);
        return (DetailVocabularyDto) modelMapperObject.mapToObject(detailVocabularyRepository.save(entity),DetailVocabularyDto.class);
    }


    @Override
    public void delete(Integer id) {
        detailVocabularyRepository.deleteById(id);
    }


    @Override
    public List<DetailVocabularyDto> getByExerciseVocabulary(VocabularyLessonDto dto) {
        List<DetailVocabulary> detailVocabularies = detailVocabularyRepository.findByExerciseVocabulary(dto.getId());
        return modelMapperObject.mapToList(detailVocabularies,DetailVocabularyDto.class);
    }

}
