package com.example.auth.commons.service;

import com.example.auth.commons.model.RestAPI;
import com.example.auth.commons.repository.RestAPIRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestAPIServiceImpl implements RestAPIService{
    private  final RestAPIRepository restAPIRepository;

    public RestAPIServiceImpl(RestAPIRepository restAPIRepository) {
        this.restAPIRepository = restAPIRepository;
    }

    @Override
    public List<RestAPI> getAllAuthAPIs() {
        return restAPIRepository.findAllBy();
    }

    @Override
    public boolean hasAccess(List<String> roles, String name) {
        return restAPIRepository.existsByRolesInAndName(roles,name);

    }
}
