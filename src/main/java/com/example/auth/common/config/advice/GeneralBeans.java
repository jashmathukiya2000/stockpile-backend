package com.example.auth.common.config.advice;

import com.example.auth.decorator.Response;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralBeans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public NullAwareBeanUtilsBean beanUtilsBean() {
        return new NullAwareBeanUtilsBean();
    }
    @Bean
    public Response beanResponse() {
        return new Response();
    }
}
