package com.english.service.impl;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.english.entities.QuestionReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.english.repository.QuestionReadingRepository;

@Service
public class QuestionReadingServiceImpl {
	@Autowired
	private QuestionReadingRepository questionReadingRepository;
	
	public Page<QuestionReading> findByReadingId(int page, int size, long readingId) {
		return questionReadingRepository.findByReadingId(readingId, PageRequest.of(page - 1, size));
	}

	
	@Transactional
	public QuestionReading save(QuestionReading questionReading)
			throws IOException {
		return questionReadingRepository.save(questionReading);
	}
	
	public void deleteCauHoiBaiTapDoc(long id) {
		questionReadingRepository.deleteById(id);
	}

}
