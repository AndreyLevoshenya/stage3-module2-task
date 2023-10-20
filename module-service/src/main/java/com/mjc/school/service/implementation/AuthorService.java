package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotations.OnDelete;
import com.mjc.school.service.annotations.ValidateDto;
import com.mjc.school.service.annotations.ValidateId;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidationException;
import com.mjc.school.service.mappers.AuthorDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.AUTHOR_DOES_NOT_EXIST;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseRepository<AuthorModel, Long> repository;
    private final AuthorDtoMapper mapper;

    @Autowired
    public AuthorService(BaseRepository<AuthorModel, Long> repository) {
        this.repository = repository;
        mapper = new AuthorDtoMapper();
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return repository.readAll().stream().map(mapper::modelToDto).toList();
    }

    @Override
    @ValidateId
    public AuthorDtoResponse readById(Long id) {
        if(repository.existById(id)) {
            return mapper.modelToDto(repository.readById(id).get());
        }
        else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }

    @Override
    @ValidateDto
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel model = mapper.dtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreateDate(date);
        model.setLastUpdateDate(date);
        return mapper.modelToDto(repository.create(model));
    }

    @Override
    @ValidateId
    @ValidateDto
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if(repository.existById(updateRequest.getId())) {
            AuthorModel model = mapper.dtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdateDate(date);
            return mapper.modelToDto(repository.update(model));
        }
        else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), updateRequest.getId()));
        }
    }


    @Override
    @ValidateId
    @OnDelete
    public boolean deleteById(Long id) {
        if(repository.existById(id)) {
            return repository.deleteById(id);
        }
        else {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
        }
    }
}
