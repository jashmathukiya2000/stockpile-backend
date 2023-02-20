//package com.example.auth.commons.config;
//
//
//import com.google.api.client.util.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfig {
//
//    @Value("${spring.mail.host}")
//    private String smtpHost;
//
//    @Value("${spring.mail.port}")
//    private int smtpPort;
//
//    @Value("${spring.mail.username}")
//    private String smtpUsername;
//
//    @Value("${spring.mail.password}")
//    private String smtpPassword;
//
//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String smtpAuth;
//
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String smtpStarttlsEnable;
//
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(smtpHost);
//        mailSender.setPort(smtpPort);
//
//        mailSender.setUsername(smtpUsername);
//        mailSender.setPassword(smtpPassword);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", smtpAuth);
//        props.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//}