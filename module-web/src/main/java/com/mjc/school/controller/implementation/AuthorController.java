package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service;
    @Autowired
    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> service) {
        this.service = service;
    }

    @CommandHandler(id = 6)
    @Override
    public List<AuthorDtoResponse> readAll() {
        return service.readAll();
    }

    @CommandHandler(id = 7)
    @Override
    public AuthorDtoResponse readById(@CommandParam Long id) {
        return service.readById(id);
    }

    @CommandHandler(id = 8)
    @Override
    public AuthorDtoResponse create(@CommandBody AuthorDtoRequest createRequest) {
        return service.create(createRequest);
    }

    @CommandHandler(id = 9)
    @Override
    public AuthorDtoResponse update(@CommandBody AuthorDtoRequest updateRequest) {
        return service.update(updateRequest);
    }

    @CommandHandler(id = 10)
    @Override
    public boolean deleteById(@CommandParam Long id) {
        return service.deleteById(id);
    }
}
