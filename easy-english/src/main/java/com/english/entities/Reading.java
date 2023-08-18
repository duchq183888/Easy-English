package com.english.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reading_lesson")
public class Reading {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	private Integer level;
	
	@Transient
	@JsonIgnore
	private MultipartFile photo;
	
	private Integer part;
	
	@JsonIgnore
	@Transient
	private MultipartFile fileQuestion;
	
	@Column(columnDefinition = "TEXT")
	private String script;
	
	@OneToMany(mappedBy = "reading", cascade = CascadeType.ALL, orphanRemoval = true)
	List<QuestionReading> questionReadings;


}
