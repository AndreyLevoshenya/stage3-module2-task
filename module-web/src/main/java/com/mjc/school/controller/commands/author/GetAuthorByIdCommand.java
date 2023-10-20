package com.mjc.school.controller.commands.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.controller.commands.Command;
import com.mjc.school.controller.commands.Operation;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.mjc.school.controller.utils.Constants.AUTHOR_ID;
import static com.mjc.school.controller.utils.Constants.ENTER_AUTHOR_ID;
import static com.mjc.school.controller.utils.Utils.getLongFromKeyboard;

@Component
public class GetAuthorByIdCommand implements Command {
    private static final Operation operation = Operation.GET_AUTHOR_BY_ID;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller;

    public GetAuthorByIdCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.GET_AUTHOR_BY_ID.getOperation());
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(CommandParam.class)) {
                        System.out.println(ENTER_AUTHOR_ID);
                        Long param = getLongFromKeyboard(AUTHOR_ID);
                        try {
                            AuthorDtoResponse authorDtoResponse = (AuthorDtoResponse) method.invoke(controller, param);
                            if (authorDtoResponse != null) {
                                System.out.println(authorDtoResponse);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            System.out.println(e.getCause().getMessage());
                        }
                    }
                }
                break;
            }
        }
    }
}
