package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.modelmapper.ModelMapper;

public class NewsDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public NewsDtoResponse modelToDto(NewsModel news) {
            return modelMapper.map(news, NewsDtoResponse.class);
    }
    public NewsModel dtoToModel(NewsDtoRequest dtoRequest) {
            return modelMapper.map(dtoRequest, NewsModel.class);
        }

}
