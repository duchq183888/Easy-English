package com.english.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionReadingDto {
    private Long id;

    private String numOrder;

    private String paragraph;

    private String question;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String correctAnswer;

    private String explanation;

    @JsonIgnore
    private ReadingDto reading;
}
