package com.example.auth.commons.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.decorator.AdminResponse;
import com.example.auth.commons.model.AdminConfiguration;
import com.example.auth.commons.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;


@Service
@Slf4j
public class AdminConfigurationServiceImpl implements AdminConfigurationService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    @Override
    public AdminResponse addConfiguration()  {
        AdminConfiguration adminConfiguration = new AdminConfiguration();
        if (!CollectionUtils.isEmpty(adminRepository.findAll())) {
            adminConfiguration = adminRepository.findAll().get(0);
        } else {
            adminConfiguration = adminRepository.save(adminConfiguration);
        }
        AdminResponse adminResponse = new AdminResponse();
        try {
            nullAwareBeanUtilsBean.copyProperties(adminResponse, adminConfiguration);
        } catch (InvocationTargetException | IllegalAccessException  e) {
            log.error("error occured when mapping model to dto : {}",e.getMessage(), e);
        }
        return adminResponse;
    }

    @Override
    public AdminConfiguration getConfiguration()  {
        AdminConfiguration adminConfiguration = new AdminConfiguration();
        if (adminRepository.findAll().isEmpty()) {
            adminRepository.save(adminConfiguration);
        } else {
            AdminConfiguration adminConfiguration1 = adminRepository.findAll().get(0);
            try {
                nullAwareBeanUtilsBean.copyProperties(adminConfiguration1, adminConfiguration);
            }  catch (InvocationTargetException | IllegalAccessException e) {
                log.error("error occured when mapping model to dto : {}",e.getMessage(), e);
            }
            adminRepository.save(adminConfiguration1);
        }
        return adminConfiguration;
    }

}
