package com.mjc.school.controller.utils;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ValidationException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mjc.school.controller.utils.Constants.*;
import static com.mjc.school.service.exceptions.ExceptionErrorCodes.ID_IS_WRONG;

public class Utils {
    public static NewsDtoRequest getNewsDtoRequestFromKeyboard() {
        System.out.println(ENTER_NEWS_TITLE);
        String title = new Scanner(System.in).nextLine();
        System.out.println(ENTER_NEWS_CONTENT);
        String content = new Scanner(System.in).nextLine();
        System.out.println(ENTER_AUTHOR_ID);
        Long authorId = getLongFromKeyboard(AUTHOR_ID);
        return new NewsDtoRequest(null, title, content, authorId);
    }

    public static AuthorDtoRequest getAuthorDtoRequestFromKeyboard() {
        System.out.println(ENTER_AUTHOR_NAME);
        String name = new Scanner(System.in).nextLine();
        return new AuthorDtoRequest(null, name);
    }

    public static Long getLongFromKeyboard(String param) {
        Long id = null;
        boolean idIsValid = false;
        while (!idIsValid) {
            try {
                id = new Scanner(System.in).nextLong();
                idIsValid = true;
            } catch (InputMismatchException e) {
                if (param.equals(NEWS_ID)) {
                    throw new ValidationException(String.format(ID_IS_WRONG.getErrorMessage(), NEWS_ID));
                }
                if (param.equals(AUTHOR_ID)) {
                    throw new ValidationException(String.format(ID_IS_WRONG.getErrorMessage(), AUTHOR_ID));
                }
            }
        }
        return id;
    }
}
