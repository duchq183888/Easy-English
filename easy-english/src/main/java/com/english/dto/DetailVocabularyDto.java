package com.english.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailVocabularyDto {
    private Integer id;

    private Integer number;

    private String content;

    private String transcribe;

    private String image;

    private String audiomp3;

    private String meaning;

    private String sentence;

    @JsonIgnore
    private VocabularyLessonDto exerciseVocabulary;
}
