package com.mjc.school.controller.commands.news;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.Command;
import com.mjc.school.controller.commands.Operation;
import com.mjc.school.controller.annotations.CommandBody;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.mjc.school.controller.utils.Constants.ENTER_NEWS_ID;
import static com.mjc.school.controller.utils.Constants.NEWS_ID;
import static com.mjc.school.controller.utils.Utils.getLongFromKeyboard;
import static com.mjc.school.controller.utils.Utils.getNewsDtoRequestFromKeyboard;

@Component
public class UpdateNewsCommand implements Command {
    private static final Operation operation = Operation.UPDATE_NEWS;
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller;

    public UpdateNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.UPDATE_NEWS.getOperation());
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(CommandBody.class)) {
                        System.out.println(ENTER_NEWS_ID);
                        Long id = getLongFromKeyboard(NEWS_ID);
                        NewsDtoRequest request = getNewsDtoRequestFromKeyboard();
                        request.setId(id);
                        try {
                            NewsDtoResponse newsDtoResponse = (NewsDtoResponse) method.invoke(controller, request);
                            if(newsDtoResponse != null) {
                                System.out.println(newsDtoResponse);
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
