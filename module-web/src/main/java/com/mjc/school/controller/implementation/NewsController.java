package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> service;

    @Autowired
    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> service) {
        this.service = service;
    }

    @CommandHandler(id = 1)
    @Override
    public List<NewsDtoResponse> readAll() {
        return service.readAll();
    }

    @CommandHandler(id = 2)
    @Override
    public NewsDtoResponse readById(@CommandParam Long id) {
        return service.readById(id);
    }

    @CommandHandler(id = 3)
    @Override
    public NewsDtoResponse create(@CommandBody NewsDtoRequest createRequest) {
        return service.create(createRequest);
    }

    @CommandHandler(id = 4)
    @Override
    public NewsDtoResponse update(@CommandBody NewsDtoRequest updateRequest) {
        return service.update(updateRequest);
    }

    @CommandHandler(id = 5)
    @Override
    public boolean deleteById(@CommandParam Long id) {
        return service.deleteById(id);
    }
}
