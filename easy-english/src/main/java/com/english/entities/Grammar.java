package com.english.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "grammar")
public class Grammar {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(columnDefinition = "TEXT",name="content_HTML")
	private String contentHTML;
	
	@Column(columnDefinition = "TEXT",name="content_MarkDown")
	private String contentMarkDown;


}
