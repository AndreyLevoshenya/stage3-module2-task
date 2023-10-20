package com.mjc.school.service.implementation.config;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsTestConfig {
    @Bean
    public NewsRepository newsRepository() {
        return new NewsRepository();
    }

    @Bean
    public NewsService newsService() {
        return new NewsService(newsRepository());
    }
}
