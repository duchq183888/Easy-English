package com.english.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question_reading")
public class QuestionReading {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @Column(name = "num_order")
	private String numOrder;

	@Column(columnDefinition = "TEXT")
	private String paragraph;

	@Column(name = "question")
	private String question;

	@Column(name = "option1")
	private String option1;

	@Column(name = "option2")
	private String option2;

	@Column(name = "option3")
	private String option3;

	@Column(name = "option4")
	private String option4;

	@Column
	private String correctAnswer;

	@Column(name = "explanation")
	private String explanation;

	@ManyToOne
	@JoinColumn(name = "reading_id")
	private Reading reading;

}
