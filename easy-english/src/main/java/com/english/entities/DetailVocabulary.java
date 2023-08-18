package com.english.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DetailVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "content")
    private String content;

    @Column(name = "transcribe")
    private String transcribe;

    @Column(name = "image")
    private String image;

    @Column(name = "audiomp3")
    private String audiomp3;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "sentence")
    private String sentence;

    @ManyToOne
    @JoinColumn(name = "vocabulary_lesson_id", nullable = false)
    private VocabularyLesson vocabularyLesson;


}
