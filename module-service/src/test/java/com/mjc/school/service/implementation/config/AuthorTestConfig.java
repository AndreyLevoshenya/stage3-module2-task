package com.mjc.school.service.implementation.config;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.service.implementation.AuthorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorTestConfig {
    @Bean
    public AuthorRepository authorRepository() {
        return new AuthorRepository();
    }

    @Bean
    public AuthorService authorService() {
        return new AuthorService(authorRepository());
    }
}
