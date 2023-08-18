package com.english.repository;

import java.util.List;

import com.english.entities.Role;
import com.english.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);

	User findByEmail(String email);
	
	Page<User> findByRole(Role role, Pageable of);

	List<User> findByRole(Role role);

	boolean existsById(Long id);
}
