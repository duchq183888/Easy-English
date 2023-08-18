package com.english.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.english.entities.Reading;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReadingDto {
    private Long id;

    private String name;

    private Integer level;

    @JsonIgnore
    private MultipartFile photo;

    private int part;

    @JsonIgnore
    private MultipartFile fileQuestion;

    private String script;

    private List<QuestionReadingDto> questionReadings;

    public ReadingDto(Reading entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.level = entity.getLevel();
            this.photo = entity.getPhoto();
            this.part = entity.getPart();
            this.fileQuestion = entity.getFileQuestion();
            this.script = entity.getScript();
        }
    }
}
