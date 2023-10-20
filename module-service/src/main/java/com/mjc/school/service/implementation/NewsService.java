package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotations.ValidateDto;
import com.mjc.school.service.annotations.ValidateId;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mappers.NewsDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.NEWS_DOES_NOT_EXIST;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {
    private final BaseRepository<NewsModel, Long> repository;
    private final NewsDtoMapper mapper;

    @Autowired
    public NewsService(BaseRepository<NewsModel, Long> repository) {
        this.repository = repository;
        mapper = new NewsDtoMapper();
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return repository.readAll().stream().map(mapper::modelToDto).toList();
    }

    @Override
    @ValidateId
    public NewsDtoResponse readById(Long id) {
        if (repository.existById(id)) {
            return mapper.modelToDto(repository.readById(id).get());
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @ValidateDto
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        NewsModel model = mapper.dtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreateDate(date);
        model.setLastUpdatedDate(date);
        return mapper.modelToDto(repository.create(model));
    }

    @Override
    @ValidateId
    @ValidateDto
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (repository.existById(updateRequest.getId())) {
            NewsModel model = mapper.dtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdatedDate(date);
            return mapper.modelToDto(repository.update(model));
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), updateRequest.getId()));
        }
    }

    @Override
    @ValidateId
    public boolean deleteById(Long id) {
        if (repository.existById(id)) {
            return repository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }
}
