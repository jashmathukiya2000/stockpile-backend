package com.example.auth.service;

import com.example.auth.commons.JWTUser;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.decorator.ExcelUtils;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.commons.model.AdminConfiguration;
import com.example.auth.commons.model.EmailModel;
import com.example.auth.commons.service.AdminConfigurationService;
import com.example.auth.commons.utils.JwtTokenUtil;
import com.example.auth.commons.utils.Utils;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.decorator.user.*;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserHelper userHelper;
    private final AdminConfigurationService adminConfigurationService;
    private final Utils utils;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, JwtTokenUtil jwtTokenUtil, UserHelper userHelper, AdminConfigurationService adminConfigurationService, Utils utils ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userHelper = userHelper;
        this.adminConfigurationService = adminConfigurationService;
        this.utils = utils;

    }

    @Override
    public UserResponse addUser(UserAddRequest userAddRequest) {
        User user = modelMapper.map(userAddRequest, User.class);
        user.setFullName(userHelper.getFullName(user));
        UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
        userRepository.save(user);
        return userResponse1;
    }

    @Override
    public void updateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        User user1 = getUserModel(id);
        update(id, userAddRequest);
        userHelper.difference(userAddRequest, user1);
//       difference(userAddRequest, user1);


    }


    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAllBySoftDeleteFalse();
        List<UserResponse> userResponseList = new ArrayList<>();
        users.forEach(user -> {
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse1);
        });

        return userResponseList;
    }

    @Override
    public UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException {
        User user = getUserModel(id);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = getUserModel(id);
        user.setSoftDelete(true);
        userRepository.save(user);
    }

    @Override
    public UserResponse getToken(String id) throws InvocationTargetException, IllegalAccessException {
        User user = getUserModel(id);
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(user.getRole());
        JWTUser jwtUser = new JWTUser(id, Collections.singletonList(userResponse.getRole().toString()));
        String token = jwtTokenUtil.generateToken(jwtUser);
        nullAwareBeanUtilsBean.copyProperties(userResponse, user);
        userResponse.setToken(token);
        userResponse.setId(user.getId());
        return userResponse;
    }

    @Override
    public String getIdFromToken(String token) {
        String id = jwtTokenUtil.getUserIdFromToken(token);
        boolean exist = userRepository.existsById(id);
        if (exist) {
            return id;
        } else {
            throw new InvalidRequestException(MessageConstant.INVALID_TOKEN);
        }

    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        Date date = jwtTokenUtil.getExpirationDateFromToken(token);
        return date;
    }

    @Override
    public boolean isTokenExpired(String token) {
        boolean expiration = jwtTokenUtil.isTokenExpired(token);
        if (expiration) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Workbook getAllUserInExcel() throws InvocationTargetException, IllegalAccessException {
        List<UserResponse> userResponses = getAllUser();
        List<UserExcelResponse> list = new ArrayList<>();
        //list add excel class object
        for (UserResponse response : userResponses) {
            UserExcelResponse userExcelResponse = new UserExcelResponse();
            //excel class obj
            nullAwareBeanUtilsBean.copyProperties(userExcelResponse, response);
            //add excel class list
            list.add(userExcelResponse);
        }
        return ExcelUtils.createWorkbookFromData(list, "UserDetails");
    }


    @Override
    public Workbook getUserDetailsByResultSpi(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest) throws InvocationTargetException, IllegalAccessException, JSONException {
        HashMap<String, List<UserSpiDataInExcel>> hashMap = new LinkedHashMap<>();
        Page<UserSpiResponse> page = userRepository.getUserDetailsByResultSpi(filter, sort, pageRequest);
        List<UserSpiResponse> list = page.getContent();
        list.forEach(userSpiResponse -> {
            List<UserSpiDataInExcel> userSpiDataInExcels = new ArrayList<>();
            userSpiResponse.getAuth().forEach(userSpiData -> {
                UserSpiDataInExcel userSpiDataInExcel = new UserSpiDataInExcel();
                try {
                    nullAwareBeanUtilsBean.copyProperties(userSpiDataInExcel, userSpiData);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                userSpiDataInExcels.add(userSpiDataInExcel);
            });
            hashMap.put(userSpiResponse.get_id(), userSpiDataInExcels);
        });
        Workbook workbook = ExcelUtils.createWorkbookOnResultSpi(hashMap, "UserDetailsBySpi");
        return workbook;
    }


    private void createFileAndSendEmail(Workbook workBook) {
        try {
            File file = new File("UserData.xlsx");
            ByteArrayResource resource = ExcelUtils.getBiteResourceFromWorkbook(workBook);
            FileUtils.writeByteArrayToFile(file, resource.getByteArray());
            File path = new File("C:\\Users\\TRPC05\\Downloads" + file.getName());
            path.createNewFile();
            sendmail(path);
        } catch (Exception e) {
            log.error("Error happened in excel generation or send email of excel: {}", e.getMessage());
        }
    }

    private void sendmail(File file) throws InvocationTargetException, IllegalAccessException {

        AdminConfiguration adminConfiguration = adminConfigurationService.getConfiguration();
        try {
            EmailModel emailModel = new EmailModel();
            emailModel.setTo("sanskriti.s@techroversolutions.com");
            System.out.println(emailModel.getTo());
            emailModel.setCc(adminConfiguration.getTechAdmins());
            System.out.println(emailModel.getCc());
            emailModel.setSubject("UserData");
            emailModel.setFile(file);
            utils.sendEmailNow(emailModel);
        } catch (Exception e) {
            log.error("Error happened while sending result to user :{}", e.getMessage());
        }
    }







    @Override
    public Page<UserEligibilityAggregation> getUserEligibilityByAge(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pagination) throws JSONException {
        return userRepository.getUserEligibilityByAge(filter, sort, pagination);
    }


    @Override
    public List<UserResponse> getUserByAge(UserFilter userFilter) {
        return userRepository.getByFilterAndSoftDeleteFalse(userFilter);
    }

    @Override
    public List<UserAggregationResponse> getUserBySalary(UserFilter userFilter) {
        return userRepository.getUserByAggregation(userFilter);
    }

    @Override
    public Page<UserResponse> getAllUserByPagination(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest) {
        return userRepository.getAllUserByPagination(filter, sort, pageRequest);
    }

    @Override
    public List<MaxSpiResponse> getUserByMaxSpi(String id) {
        return userRepository.getUserByMaxSpi(id);
    }


    public User getUserModel(String id) {

        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }

    public void update(String id, UserAddRequest userAddRequest) {
        User user = getUserModel(id);
        if (userAddRequest.getFirstName() != null) {
            user.setFirstName(userAddRequest.getFirstName());
            user.setFullName(userHelper.getFullName(user));
        }
        if (userAddRequest.getEmail() != null) {
            user.setEmail(userAddRequest.getEmail());
        }
        if (userAddRequest.getOccupation() != null) {
            user.setOccupation(userAddRequest.getOccupation());
        }
        if (userAddRequest.getAddress() != null) {
            user.setAddress(userAddRequest.getAddress());
        }
        if (userAddRequest.getSalary() > 0) {
            user.setSalary(userAddRequest.getSalary());

        }
        if (userAddRequest.getMiddleName() != null) {
            user.setMiddleName(userAddRequest.getMiddleName());
        }
        if (userAddRequest.getAge() > 0) {
            user.setAge(userAddRequest.getAge());
        }
        if (userAddRequest.getLastName() != null) {
            user.setLastName(userAddRequest.getLastName());
        }
        userRepository.save(user);

    }

}


