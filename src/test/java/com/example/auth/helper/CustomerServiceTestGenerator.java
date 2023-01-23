package com.example.auth.helper;

import com.example.auth.commons.enums.Role;
import com.example.auth.commons.model.AdminConfiguration;
import com.example.auth.commons.model.EmailModel;
import com.example.auth.decorator.customer.CustomerAddRequest;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerResponse;
import com.example.auth.model.Customer;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CustomerServiceTestGenerator {

    private static final String email = "sanskritishukla4@gmail.com";
    private static final String otp = "497172";
    private static final String name = "sans";
    private static final String userName = "sansshukla";
    private static final Set<String> techAdmins  = Collections.singleton("sanskriti.s@techroversolutions.com");
    private final AdminConfiguration adminConfiguration= Mockito.mock(AdminConfiguration.class);

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static Customer getMockuserModel(String passwords) {
        return Customer.builder()
                .name(name)
                .email(email)
                .otpSendtime(new Date())
                .password(passwords)
                .role(Role.USER)
                .loginTime(new Date())
                .userName(userName)
                .contact("6386580393")
                .build();
    }

    public static CustomerAddRequest getMockUserAddRequest() {
        return CustomerAddRequest.builder()
                .email(email)
                .password("Sans@12345")
                .contact("6386580393")
                .userName(userName)
                .confirmPassword("Sans@12345")
                .name(name)
                .build();

    }

    public static CustomerResponse getMockSignUpResponse(String password) {
        return CustomerResponse.builder()
                .email(email)
                .contact("6386580393")
                .password(password)
                .name(name)
                .userName("sansshukla")
                .role(Role.USER)
                .build();
    }

    public static CustomerLoginAddRequest getMockLoginRequest() {
        return CustomerLoginAddRequest.builder()
                .email(email)
                .password("Sans@12345")
                .build();
    }

    public static CustomerResponse getMockLoginResponse(String password, String otp) {
        return CustomerResponse.builder()
                .password(password)
                .email(email)
                .role(Role.USER)
                .name(name)
                .otp(otp)
                .userName(userName)
                .contact("6386580393")
                .build();
    }

    public static AdminConfiguration getMockAdminConfiguration() {
        return AdminConfiguration.builder()
                .mobileNoRegex("^[0-9]{10}$")
                .nameRegex("^[a-zA-Z]+$")
                .passwordRegex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,15}$")
                .emailRegex("^[A-Za-z0-9+_.-]+@(.+)$")
                .username("^[a-zA-Z]+$")
                .build();
    }

    public static List<Customer> getMockAllCustomer(String otp,String password) {
        return List.of(Customer.builder().name(name).userName(userName)
                .contact("6386580393")
                .password(password)
                .otp(otp)
                .email(email)
                .role(Role.USER)
                .build()
        );
    }

    public static List<CustomerResponse> getMockAllCustomerResponse(String password,String otp){
        return List.of(CustomerResponse.builder()
                .email(email)
                .contact("6386580393")
                .password(password)
                .name(name)
                .otp(otp)
                .userName("sansshukla")
                .role(Role.USER).build());
    }

    public static EmailModel getMockEmailModel(String otp){
        return EmailModel.builder()
                .cc(techAdmins)
                .to("sanskriti.s@techroversolutions.com")
                .message(otp)
                .subject("OTP Verfication")
                .build();


    }

}
