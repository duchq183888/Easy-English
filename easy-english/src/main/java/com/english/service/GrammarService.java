package com.english.service;

import java.util.List;

import com.english.dto.GrammarDto;
import com.english.entities.Grammar;
import org.springframework.data.domain.Page;

public interface GrammarService {
    GrammarDto save(GrammarDto grammar);

    GrammarDto getById(int id);

    Page<GrammarDto> getAll(int page, int limit);

    List<GrammarDto> getAll();

    void delete(int id);

    List<GrammarDto> search(String search);

}
