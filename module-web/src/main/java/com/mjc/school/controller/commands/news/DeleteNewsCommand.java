package com.mjc.school.controller.commands.news;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.annotations.CommandParam;
import com.mjc.school.controller.commands.Command;
import com.mjc.school.controller.commands.Operation;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.mjc.school.controller.utils.Constants.ENTER_NEWS_ID;
import static com.mjc.school.controller.utils.Constants.NEWS_ID;
import static com.mjc.school.controller.utils.Utils.getLongFromKeyboard;

@Component
public class DeleteNewsCommand implements Command {
    private static final Operation operation = Operation.REMOVE_NEWS_BY_ID;
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller;

    public DeleteNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.REMOVE_NEWS_BY_ID.getOperation());
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(CommandParam.class)) {
                        System.out.println(ENTER_NEWS_ID);
                        Long param = getLongFromKeyboard(NEWS_ID);
                        try {
                            System.out.println(method.invoke(controller, param));
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
