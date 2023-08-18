package com.english.service.impl;

import java.util.List;

import com.english.dto.GrammarDto;
import com.english.entities.Grammar;
import com.english.util.ModelMapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.english.repository.GrammarRepository;
import com.english.service.GrammarService;

@Service
public class GrammarServiceImpl implements GrammarService {
	@Autowired
    private GrammarRepository grammarRepository;

	private final ModelMapperObject modelMapperObject;

	public GrammarServiceImpl(ModelMapperObject modelMapperObject) {
		this.modelMapperObject = modelMapperObject;
	}

	@Override
	public GrammarDto save(GrammarDto grammar) {
		Grammar entity = (Grammar) modelMapperObject.mapToObject(grammar,Grammar.class);
		return (GrammarDto) modelMapperObject.mapToObject(grammarRepository.save(entity),GrammarDto.class);
	}
	
	@Override
	public GrammarDto getById(int id){

		return (GrammarDto) modelMapperObject.mapToObject(grammarRepository.findById(id),GrammarDto.class);
	}
	
	@Override
	public Page<GrammarDto> getAll(int page, int size){
		Page<Grammar> entities= grammarRepository.findAll(PageRequest.of(page, size));
		Page<GrammarDto> dtos = new PageImpl<>(modelMapperObject.mapToList(entities.getContent(),GrammarDto.class),entities.getPageable(),entities.getTotalElements());
		return dtos;
		
	}
	
	@Override
	public List<GrammarDto>getAll(){

		return modelMapperObject.mapToList(grammarRepository.findAll(),GrammarDto.class);
	}
	
	@Override
	public void delete(int id) {
		grammarRepository.deleteById(id);
	}
	
	@Override
	public List<GrammarDto> search(String search){
		List<Grammar> grammars = grammarRepository.search(search);
		return modelMapperObject.mapToList(grammars,GrammarDto.class);
		
	}
}
