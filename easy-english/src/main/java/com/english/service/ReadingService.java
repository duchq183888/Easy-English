package com.english.service;

import com.english.dto.ReadingDto;
import com.english.entities.Reading;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ReadingService {
    ReadingDto save(ReadingDto dto, HttpServletRequest request);

    void delete(long id);

    ReadingDto findById(long id);

    Page<ReadingDto> findAllByPartAndLevel(int page, int size, String part, String level);

    void saveFile(ReadingDto dto, HttpServletRequest request) throws IOException;
}
