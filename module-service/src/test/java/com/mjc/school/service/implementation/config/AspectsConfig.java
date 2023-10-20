package com.mjc.school.service.implementation.config;

import com.mjc.school.service.aspects.ValidationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectsConfig {

    @Bean
    public ValidationAspect validationAspect() {
        return new ValidationAspect();
    }
}
