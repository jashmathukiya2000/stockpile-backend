package com.example.auth.commons.service;

import com.example.auth.commons.decorator.AdminResponse;
import com.example.auth.commons.model.AdminConfiguration;

import java.lang.reflect.InvocationTargetException;

public interface AdminConfigurationService {
    AdminResponse addConfiguration();

    AdminConfiguration getConfiguration() ;

}
