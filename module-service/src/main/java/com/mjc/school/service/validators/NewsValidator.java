package com.mjc.school.service.validators;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ValidationException;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.*;

public class NewsValidator {


    public static final int MAX_AUTHOR_ID = 20;

    public boolean validateNewsId(Long newsId) {
        if(newsId == null || newsId < 1) {
            throw new ValidationException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), newsId));
        }
        else {
            return true;
        }
    }

    public boolean validateAuthorId(Long authorId) {
        if(authorId == null || authorId < 1) {
            throw new ValidationException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), authorId));
        }
        else {
            return true;
        }
    }


    public boolean validateDto(NewsDtoRequest news) {
        if(news.getTitle().length() < 5 || news.getTitle().length() > 30) {
            throw new ValidationException(String.format(TITLE_LENGTH_IS_WRONG.getErrorMessage(), news.getTitle()));
        }
        else if(news.getContent().length() < 5 || news.getContent().length() > 255) {
            throw new ValidationException(String.format(CONTENT_LENGTH_IS_WRONG.getErrorMessage(), news.getContent()));        }
        else if(news.getAuthorId() > MAX_AUTHOR_ID) {
            throw new ValidationException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), news.getAuthorId()));        }
        else {
            validateNewsId(news.getId());
            validateAuthorId(news.getAuthorId());
            return true;
        }
    }
}
