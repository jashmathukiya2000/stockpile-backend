package com.example.auth.commons.advice;

import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.commons.decorator.RequestSession;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.Response;
import com.example.auth.decorator.pagination.Pagination;
import com.example.auth.model.CanvasjsChartData;
import com.example.auth.model.PurchaseLogHistory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

    @Configuration
    public class GeneralBeans {
//        @Value("${trs.defaults.prefetchCount}")
//        String prefetchCount;
//
//        @Value("${trs.defaults.concurrentConsumers}")
//        String concurrentConsumers;



        @Bean
        public Response beanResponse() {
            return new Response();
        }
        @Bean
        ModelMapper getModelMapper(){
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return modelMapper;
        }

        @Bean
        public GeneralHelper getGeneralHelper(){
            return new GeneralHelper();
        }


        @Bean
        public RequestSession getRequestSession()
        {
            return new RequestSession();
        }

        @Bean
        public NullAwareBeanUtilsBean beanUtilsBean(){
            return new NullAwareBeanUtilsBean();
        }

        @Bean
        public UserHelper getUserHelper(){
            return new UserHelper();
        }

        @Bean
        public CanvasjsChartData getCanvasjsChartData()
        {
            return new CanvasjsChartData();
        }
//        @Bean
//        public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
//            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//            factory.setConnectionFactory(rabbitConnectionFactory);
//            factory.setPrefetchCount(Integer.valueOf(prefetchCount));
//            factory.setConcurrentConsumers(Integer.valueOf(concurrentConsumers));
//            return factory;
//        }

        @Bean
        public JavaMailSender javaMailSender() {
            return new JavaMailSenderImpl();
        }
       @Bean
        public PurchaseLogHistory purchaseLogHistory(){
            return new PurchaseLogHistory();
       }
    }



