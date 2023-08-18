package com.english.repository;

import java.util.List;

import com.english.entities.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarRepository extends JpaRepository<Grammar,Integer> {
	Grammar findById(int id);
	
	@Query("select entity FROM Grammar entity WHERE entity.name LIKE CONCAT('%',:search,'%')")
	List<Grammar> search(@Param("search") String search);
}
