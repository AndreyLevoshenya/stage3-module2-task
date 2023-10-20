package com.mjc.school.controller.commands.news;

import com.mjc.school.controller.commands.Operation;
import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.commands.Command;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class GetAllNewsCommand implements Command {
    private static final Operation operation = Operation.GET_ALL_NEWS;
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller;

    @Autowired
    public GetAllNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {

        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            if (operation.getOperationNumber().equals(method.getAnnotation(CommandHandler.class).id())) {
                System.out.println(Operation.GET_ALL_NEWS.getOperation());
                try {
                    List<NewsDtoResponse> newsDtoResponseList = (List<NewsDtoResponse>) method.invoke(controller);
                    if(newsDtoResponseList != null) {
                        for (NewsDtoResponse newsDtoResponse : newsDtoResponseList) {
                            System.out.println(newsDtoResponse);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println(e.getCause().getMessage());
                }
                break;
            }
        }
    }
}
