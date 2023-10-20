package com.mjc.school.controller.commands.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
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
import static com.mjc.school.controller.utils.Utils.getAuthorDtoRequestFromKeyboard;
import static com.mjc.school.controller.utils.Utils.getLongFromKeyboard;

@Component
public class UpdateAuthorCommand implements Command {
    private static final Operation operation = Operation.UPDATE_AUTHOR;
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller;

    public UpdateAuthorCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.UPDATE_AUTHOR.getOperation());
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(CommandBody.class)) {
                        System.out.println(ENTER_AUTHOR_ID);
                        Long id = getLongFromKeyboard(AUTHOR_ID);
                        AuthorDtoRequest request = getAuthorDtoRequestFromKeyboard();
                        request.setId(id);
                        try {
                            AuthorDtoResponse authorDtoResponse = (AuthorDtoResponse) method.invoke(controller, request);
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
