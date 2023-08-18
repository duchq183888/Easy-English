package com.english.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModelMapperObject<U, V> {
    private final ModelMapper modelMapper;

    public U mapToObject(V source, Class<U> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public List<U> mapToList(List<V> sources, Class<U> targetClass) {
        return sources.stream().
                map(element -> modelMapper.map(element, targetClass)).
//                       map(this::mapToObject). if only have source
        collect(Collectors.toList());
    }

}
