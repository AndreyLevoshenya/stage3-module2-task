package com.mjc.school.service.aspects;

import com.mjc.school.service.annotations.Id;
import com.mjc.school.service.annotations.StringField;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;

import static com.mjc.school.service.exceptions.ExceptionErrorCodes.*;

@Aspect
@Component
public class ValidationAspect {
    public static final String NEWS_SERVICE_CLASS_NAME = "NewsService";
    public static final String AUTHOR_SERVICE_CLASS_NAME = "AuthorService";


    @Pointcut("@annotation(com.mjc.school.service.annotations.ValidateId)")
    private void methodsWithIdParam() {
    }

    @Pointcut("@annotation(com.mjc.school.service.annotations.ValidateDto)")
    private void methodsWithDtoParam() {
    }

    @Around("methodsWithIdParam()")
    public Object validateId(ProceedingJoinPoint pjp) throws Throwable {
        Object parameter = pjp.getArgs()[0];
        if (parameter instanceof Long id) {
            if (id < 1) {
                if (NEWS_SERVICE_CLASS_NAME.equals(pjp.getClass().getSimpleName())) {
                    throw new ValidationException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
                } else if (AUTHOR_SERVICE_CLASS_NAME.equals(pjp.getClass().getSimpleName())) {
                    throw new ValidationException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
                }
            }
        } else if (parameter instanceof NewsDtoRequest newsDtoRequest) {
            Long id = newsDtoRequest.getId();
            if (id < 1) {
                throw new ValidationException(String.format(NEWS_DOES_NOT_EXIST.getErrorMessage(), id));
            }
        } else if (parameter instanceof AuthorDtoRequest authorDtoRequest) {
            Long id = authorDtoRequest.getId();
            if (id < 1) {
                throw new ValidationException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), id));
            }
        }
        return pjp.proceed();
    }

    @Around("methodsWithDtoParam()")
    public Object validateDto(ProceedingJoinPoint pjp) throws Throwable {
        Object param = pjp.getArgs()[0];
        if (param instanceof NewsDtoRequest newsDtoRequest) {
            Field[] fields = newsDtoRequest.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(StringField.class)) {
                    int minLength = field.getAnnotation(StringField.class).min();
                    int maxLength = field.getAnnotation(StringField.class).max();
                    String fieldName = field.getName();
                    int length;
                    switch (fieldName) {
                        case "title" -> {
                            length = newsDtoRequest.getTitle().length();
                            if (length < minLength || length > maxLength) {
                                throw new ValidationException(String.format(TITLE_LENGTH_IS_WRONG.getErrorMessage(), newsDtoRequest.getTitle()));
                            }
                        }
                        case "content" -> {
                            length = newsDtoRequest.getContent().length();
                            if (length < minLength || length > maxLength) {
                                throw new ValidationException(String.format(CONTENT_LENGTH_IS_WRONG.getErrorMessage(), newsDtoRequest.getContent()));
                            }
                        }
                    }
                }
                if (field.isAnnotationPresent(Id.class)) {
                    Long authorId = newsDtoRequest.getAuthorId();
                    if (authorId < 1 || authorId > 20) {
                        throw new ValidationException(String.format(AUTHOR_DOES_NOT_EXIST.getErrorMessage(), newsDtoRequest.getAuthorId()));
                    }
                }
            }
        } else if (param instanceof AuthorDtoRequest authorDtoRequest) {
            Field[] fields = authorDtoRequest.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(StringField.class)) {
                    int minLength = field.getAnnotation(StringField.class).min();
                    int maxLength = field.getAnnotation(StringField.class).max();
                    int length = authorDtoRequest.getName().length();
                    if (length < minLength || length > maxLength) {
                        throw new ValidationException(String.format(AUTHOR_NAME_LENGTH_IS_WRONG.getErrorMessage(), authorDtoRequest.getName()));
                    }
                }
            }
        }
        return pjp.proceed();
    }
}