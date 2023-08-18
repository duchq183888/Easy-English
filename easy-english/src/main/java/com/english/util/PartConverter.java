package com.english.util;

import javax.persistence.AttributeConverter;

import com.english.entities.PartToeic;


public class PartConverter implements AttributeConverter<PartToeic, Integer> {

	@Override
	public Integer convertToDatabaseColumn(PartToeic part) {
		return part.getValue();
	}

	@Override
	public PartToeic convertToEntityAttribute(Integer value) {
		for (PartToeic type : PartToeic.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException(" Illegal tagType value exception.");
	}

}
