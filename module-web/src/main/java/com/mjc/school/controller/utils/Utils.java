package com.mjc.school.controller.utils;

import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ValidationException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.mjc.school.controller.utils.Constants.*;

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
                if(param.equals(NEWS_ID)) {
                    throw new ValidationException(NEWS_ID_SHOULD_BE_A_NUMBER);
                }
                if(param.equals(AUTHOR_ID)) {
                    throw new ValidationException(AUTHOR_ID_SHOULD_BE_A_NUMBER);
                }
            }
        }
        return id;
    }
}
