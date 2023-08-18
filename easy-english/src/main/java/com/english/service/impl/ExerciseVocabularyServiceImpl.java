package com.english.service.impl;

import java.io.File;
import java.util.List;

import com.english.dto.VocabularyLessonDto;
import com.english.util.ModelMapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.english.entities.VocabularyLesson;
import com.english.repository.VocabularyLessonRepository;
import com.english.service.ExerciseVocabularyService;

@Service
public class ExerciseVocabularyServiceImpl implements ExerciseVocabularyService {
	@Autowired
    private VocabularyLessonRepository vocabularyLessonRepository;
	private final ModelMapperObject modelMapperObject;

	public ExerciseVocabularyServiceImpl(ModelMapperObject modelMapperObject) {
		this.modelMapperObject = modelMapperObject;
	}

	@Override
	public List<VocabularyLessonDto> findAll(){
		return modelMapperObject.mapToList(vocabularyLessonRepository.findAll(), VocabularyLessonDto.class);
	}
	// err
	@Override
	public VocabularyLessonDto save(VocabularyLessonDto exerciseVocabulary) {
		VocabularyLesson entity = (VocabularyLesson) modelMapperObject.mapToObject(exerciseVocabulary, VocabularyLesson.class);
		entity= vocabularyLessonRepository.save(entity);

		return (VocabularyLessonDto) modelMapperObject.mapToObject(entity, VocabularyLessonDto.class);
	}
	
	@Override
	public void delete(int id) {
        vocabularyLessonRepository.deleteById(id);
	}
	
	@Override
	public Page<VocabularyLessonDto> getAll(int page, int size){
		Page<VocabularyLesson> pages = vocabularyLessonRepository.findAll(PageRequest.of(page, size));
		List<VocabularyLessonDto> vocabularyDtos = modelMapperObject.mapToList(pages.getContent(), VocabularyLessonDto.class);
		Page<VocabularyLessonDto> dtos = new PageImpl<>(vocabularyDtos,pages.getPageable(),pages.getTotalElements());
		return dtos;
	}
	
	@Override
	public VocabularyLessonDto getById(Integer id) {
		return (VocabularyLessonDto) modelMapperObject.mapToObject(vocabularyLessonRepository.findById(id), VocabularyLessonDto.class);
	}
	
	@Override
	public VocabularyLessonDto getById(int id){
		VocabularyLesson vocabularyLesson = vocabularyLessonRepository.findById(id);
		return (VocabularyLessonDto) modelMapperObject.mapToObject(vocabularyLesson,
				VocabularyLessonDto.class);
	}
	
	@Override
	public List<VocabularyLessonDto> search(String search){
		return modelMapperObject.mapToList(vocabularyLessonRepository.searchVocab(search), VocabularyLessonDto.class);
	}

	public static void deleteFile(String path) {

		File file = new File(path);
		if (file.exists()) {
			boolean success = file.delete();
			if (success) {
				System.out.println("Xóa tệp tin thành công.");
			} else {
				System.out.println("Không thể xóa tệp tin.");
			}
		} else {
			System.out.println("Tệp tin không tồn tại.");
		}
	}
	

}
