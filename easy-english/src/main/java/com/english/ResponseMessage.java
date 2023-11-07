package com.globits.patient.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import com.globits.patient.app.ErrorMessage;
@Getter
@Setter
public class ResponseMessage<T> {
	private T data;
	private String code;
	private String message;

	public ResponseMessage(T data) {
		this.data = data;
		this.code = ErrorMessage.SUCCESS.getCode();
		this.message = ErrorMessage.SUCCESS.getMessage();
	}

	public ResponseMessage(String code, String message){
		this.code = code;
		this.message = message;
	}

	public ResponseMessage(ErrorMessage errorMessage) {
		this.code = errorMessage.getCode();
		this.message = errorMessage.getMessage();
	}

}
