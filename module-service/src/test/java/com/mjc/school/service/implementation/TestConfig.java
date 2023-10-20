package com.mjc.school.service.implementation;

import com.mjc.school.service.aspects.ValidationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(value = {"com.mjc.school.service", "com.mjc.school.repository"})
public class TestConfig {

    @Bean
    public ValidationAspect validationAspect() {
        return new ValidationAspect();
    }
}
