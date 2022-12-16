package com.example.auth.common.config.advice;

import com.example.auth.decorator.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralBeans {
    @Bean
  public   ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
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
