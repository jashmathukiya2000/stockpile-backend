package com.example.auth.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSender javaMailSender1() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("nilusroy0@gmail.com");
        javaMailSender.setPassword("NilusRoy@9773");
        javaMailSender.getJavaMailProperties().put("mail.smtp.auth", true);
        javaMailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", true);
        javaMailSender.getJavaMailProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return javaMailSender;
    }
}