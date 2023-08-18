package com.english.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VocabularyLessonDto {
    private Integer id;

    private String name;

    private String photo;

    @JsonIgnore
    private List<DetailVocabularyDto> detailVocabularies;

}
