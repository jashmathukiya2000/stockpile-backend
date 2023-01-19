package com.example.auth.controller;
import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.AdminResponse;
import com.example.auth.commons.enums.Role;
import com.example.auth.commons.model.AdminConfiguration;
import com.example.auth.commons.service.AdminConfigurationService;
import com.example.auth.decorator.DataResponse;
import com.example.auth.decorator.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("adminConfiguration")
public class AdminController {
    private final AdminConfigurationService adminConfigurationService;

    public AdminController(AdminConfigurationService adminConfigurationService) {
        this.adminConfigurationService = adminConfigurationService;
    }

    @RequestMapping(name = "addConfiguration", value = "/add", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public DataResponse<AdminResponse> addConfiguration() throws InvocationTargetException, IllegalAccessException {
        DataResponse<AdminResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(adminConfigurationService.addConfiguration());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getConfigurationDetails", value = "/getDetails", method = RequestMethod.GET)
    @Access(levels = Role.ADMIN)
    public DataResponse<AdminConfiguration> getConfigurationDetails() throws InvocationTargetException, IllegalAccessException {
        DataResponse<AdminConfiguration> dataResponse = new DataResponse<>();
        dataResponse.setData(adminConfigurationService.getConfiguration());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }
}
