package com.mjc.school.service.aspects;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class AuthorDeleteAspect {
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> service;

    @Autowired
    public AuthorDeleteAspect(BaseService<NewsDtoRequest, NewsDtoResponse, Long> service) {
        this.service = service;
    }

    @Pointcut("@annotation(com.mjc.school.service.annotations.OnDelete)")
    private void onDelete() {
    }

    @Around("onDelete()")
    public boolean deleteNews(ProceedingJoinPoint pjp) {
        Object obj = pjp.getArgs()[0];
        if (obj instanceof Long id) {
            List<Long> deleteList = new ArrayList<>();
            for (NewsDtoResponse news : service.readAll()) {
                if (id.equals(news.getAuthorId())) {
                    deleteList.add(news.getId());
                }
            }
            for (Long newsId : deleteList) {
                service.deleteById(newsId);
            }
        }
        return true;
    }
}
