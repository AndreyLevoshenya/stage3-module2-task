package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.modelmapper.ModelMapper;

public class AuthorDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public AuthorDtoResponse modelToDto(AuthorModel news) {
        return modelMapper.map(news, AuthorDtoResponse.class);
    }
    public AuthorModel dtoToModel(AuthorDtoRequest dtoRequest) {
        return modelMapper.map(dtoRequest, AuthorModel.class);
    }

}
