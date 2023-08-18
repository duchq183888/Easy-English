package com.english.repository;

import java.util.List;

import com.english.entities.QuestionReading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionReadingRepository extends JpaRepository<QuestionReading, Long> {

	Page<QuestionReading> findByReadingId(long id, Pageable pageable);

	List<QuestionReading> findById(long id);
}
