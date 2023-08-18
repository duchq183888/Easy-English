package com.english.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class VocabularyLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "vocabularyLesson", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DetailVocabulary> detailVocabularies;


}
