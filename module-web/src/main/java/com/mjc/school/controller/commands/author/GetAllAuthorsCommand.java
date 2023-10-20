package com.mjc.school.controller.commands.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.commands.Command;
import com.mjc.school.controller.commands.Operation;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class GetAllAuthorsCommand implements Command {
    private static final Operation operation = Operation.GET_ALL_AUTHORS;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller;

    public GetAllAuthorsCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.GET_ALL_AUTHORS.getOperation());
                try {
                    List<AuthorDtoResponse> authorDtoResponseList = (List<AuthorDtoResponse>) method.invoke(controller);
                    if(authorDtoResponseList != null) {
                        for (AuthorDtoResponse authorDtoResponse : authorDtoResponseList)
                        System.out.println(authorDtoResponse);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println(e.getCause().getMessage());
                }
                break;
            }
        }
    }
}
