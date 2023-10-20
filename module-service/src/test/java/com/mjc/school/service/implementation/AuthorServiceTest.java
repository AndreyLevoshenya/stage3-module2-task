package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidationException;
import com.mjc.school.service.mappers.AuthorDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableAspectJAutoProxy
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
class AuthorServiceTest {
    private BaseRepository<AuthorModel, Long> repository;
    private BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;
    private AuthorDtoMapper mapper;

    @BeforeEach
    void setUp() {
        repository = new AuthorRepository();
        service = new AuthorService(repository);
        mapper = new AuthorDtoMapper();
    }

    @Test
    void canGetAllAuthors() {
        List<AuthorDtoResponse> expected = repository.readAll().stream().map(mapper::modelToDto).toList();

        List<AuthorDtoResponse> actual = service.readAll();

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getAuthorThatExistsById() {
        Long id = 1L;

        AuthorDtoResponse expected = mapper.modelToDto(repository.readById(id).get());

        AuthorDtoResponse actual = service.readById(id);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getAuthorThatDoesNotExistsById() {
        Long id = 41L;
        assertThrows(NotFoundException.class, () -> service.readById(id));
    }

    @Test
    void createValidAuthor() {
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(null, "author");
        int sizeBefore = repository.readAll().size();
        service.create(authorDtoRequest);
        int sizeAfter = repository.readAll().size();
        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "au",
            "authorAuthorAuthor"})
    void createInvalidAuthor(String newName) {
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(null, newName);

        assertThrows(ValidationException.class, () -> service.create(authorDtoRequest));
    }

    @Test
    void updateValidAuthor() {
        Long id = 1L;
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, "newName");
        AuthorDtoResponse actual = service.update(authorDtoRequest);

        assertEquals(mapper.modelToDto(repository.readById(id).get()).toString(), actual.toString());
    }

    @Test
    void updateAuthorWithInvalidId() {
        Long id = 41L;
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, "newName");

        assertThrows(NotFoundException.class, () -> service.update(authorDtoRequest));
    }

    @ParameterizedTest
    @CsvSource( value = {
            "au",
            "authorAuthorAuthor"})
    void updateInvalidAuthor(String newName) {
        Long id = 1L;
        AuthorDtoResponse authorBeforeUpdate = mapper.modelToDto(repository.readById(id).get());
        AuthorDtoRequest authorDtoRequest = new AuthorDtoRequest(id, newName);

        assertThrows(ValidationException.class, () -> service.update(authorDtoRequest));

        AuthorDtoResponse authorAfterUpdate = mapper.modelToDto(repository.readById(id).get());

        assertEquals(authorBeforeUpdate.toString(), authorAfterUpdate.toString());
    }

    @Test
    void deleteAuthorWithValidId() {
        Long id = 4L;
        int sizeBefore = repository.readAll().size();
        boolean actual = service.deleteById(id);
        int sizeAfter = repository.readAll().size();
        assertTrue(actual);
        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    void deleteAuthorWithInvalidId() {
        Long id = 41L;
        int sizeBefore = repository.readAll().size();
        assertThrows(NotFoundException.class, () -> service.deleteById(id));
        int sizeAfter = repository.readAll().size();
        assertEquals(sizeBefore, sizeAfter);
    }
}