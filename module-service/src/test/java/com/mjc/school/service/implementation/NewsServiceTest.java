package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidationException;
import com.mjc.school.service.mappers.NewsDtoMapper;
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
class NewsServiceTest {
    private BaseRepository<NewsModel, Long> repository;
    private BaseService<NewsDtoRequest, NewsDtoResponse, Long> service;
    private NewsDtoMapper newsDtoMapper;

    @BeforeEach
    void setUp() {
        repository = new NewsRepository();
        service = new NewsService(repository);
        newsDtoMapper = new NewsDtoMapper();
    }

    @Test
    void canGetAllNews() {
        List<NewsDtoResponse> expected = repository.readAll().stream().map(newsDtoMapper::modelToDto).toList();

        List<NewsDtoResponse> actual = service.readAll();

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getNewsThatExistsById() {
        Long id = 1L;

        NewsDtoResponse expected = newsDtoMapper.modelToDto(repository.readById(id).get());

        NewsDtoResponse actual = service.readById(id);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getNewsThatDoesNotExistsById() {
        Long id = 41L;
        assertThrows(NotFoundException.class, () -> service.readById(id));
    }

    @Test
    void createValidNews() {
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(null, "title6", "content6", 2L);
        int sizeBefore = repository.readAll().size();
        service.create(newsDtoRequest);
        int sizeAfter = repository.readAll().size();
        assertEquals(sizeBefore + 1, sizeAfter);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "titl, content6, 2",
            "title6, cont, 2",
            "title6, content6, 41"})
    void createInvalidNews(String title, String content, Long authorId) {
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(null, title, content, authorId);

        assertThrows(ValidationException.class, () -> service.create(newsDtoRequest));
    }

    @Test
    void updateValidNews() {
        Long id = 1L;
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(id, "newTitle4", "newContent4", 3L);
        NewsDtoResponse actual = service.update(newsDtoRequest);

        assertEquals(newsDtoMapper.modelToDto(repository.readById(id).get()).toString(), actual.toString());
    }

    @Test
    void updateNewsWithInvalidId() {
        Long id = 41L;
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(id, "newTitle4", "newContent4", 3L);

        assertThrows(NotFoundException.class, () -> service.update(newsDtoRequest));
    }

    @ParameterizedTest
    @CsvSource( value = {
            "newT, newContent4, 3",
            "newTitle4, newC, 3",
            "newTitle4, newContent4, 41"})
    void updateInvalidNews(String newTitle, String newContent, Long newAuthorId) {
        Long id = 1L;
        NewsDtoResponse newsBeforeUpdate = newsDtoMapper.modelToDto(repository.readById(id).get());
        NewsDtoRequest newsDtoRequest = new NewsDtoRequest(id, newTitle, newContent, newAuthorId);

        assertThrows(ValidationException.class, () -> service.update(newsDtoRequest));

        NewsDtoResponse newsAfterUpdate = newsDtoMapper.modelToDto(repository.readById(id).get());

        assertEquals(newsBeforeUpdate.toString(), newsAfterUpdate.toString());
    }

    @Test
    void deleteNewsWithValidId() {
        Long id = 4L;
        int sizeBefore = repository.readAll().size();
        boolean actual = service.deleteById(id);
        int sizeAfter = repository.readAll().size();
        assertTrue(actual);
        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    void deleteNewsWithInvalidId() {
        Long id = 41L;
        int sizeBefore = repository.readAll().size();
        assertThrows(NotFoundException.class, () -> service.deleteById(id));
        int sizeAfter = repository.readAll().size();
        assertEquals(sizeBefore, sizeAfter);
    }
}