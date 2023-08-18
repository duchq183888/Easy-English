package com.english.rest;

import com.english.dto.ReadingDto;
import com.english.entities.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.english.entities.QuestionReading;
import com.english.service.impl.ReadingServiceImpl;
import com.english.service.impl.QuestionReadingServiceImpl;

@RestController
@RequestMapping("api/client/readings")
public class ReadingClientApi {
	
	@Autowired
	private ReadingServiceImpl readingService;
	
	@Autowired
	private QuestionReadingServiceImpl questionReadingService;

	@GetMapping("/baiDocId={baiDocId}")
	public ResponseEntity<Page<QuestionReading>> getQuestionReadingsByReadingId(
			@RequestParam(defaultValue = "1") int page, @PathVariable long baiDocId) {
		return new ResponseEntity<>(questionReadingService.findByReadingId(page, 3, baiDocId), HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<Page<ReadingDto>> findAllByPartAndlevel(@RequestParam(defaultValue = "1") int page,
																  @RequestParam(defaultValue = "") String part, @RequestParam(defaultValue = "") String level) {
		return new ResponseEntity<>(readingService.findAllByPartAndLevel(page, 5, part, level), HttpStatus.OK);
	}

}
