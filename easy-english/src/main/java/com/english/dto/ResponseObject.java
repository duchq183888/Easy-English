package com.english.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class ResponseObject {

    private Object data;
    private Map<String, String> errorMessages;
    private String status;

}
