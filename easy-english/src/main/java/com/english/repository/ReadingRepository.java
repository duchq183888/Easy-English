package com.english.repository;

import com.english.dto.ReadingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.english.entities.Reading;
import org.springframework.data.jpa.repository.Query;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
	Page<Reading> findByPartAndLevel(int part, int level, Pageable pageable);

	Reading findById(long id);

	boolean existsById(Long id);

}
