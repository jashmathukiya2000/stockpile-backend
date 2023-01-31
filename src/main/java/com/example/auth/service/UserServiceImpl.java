package com.example.auth.service;

import com.example.auth.commons.JWTUser;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.commons.utils.JwtTokenUtil;
import com.example.auth.decorator.ExcelUtils;
import com.example.auth.decorator.UserDataExcel;
import com.example.auth.decorator.UserExcelResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.decorator.user.*;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserHelper userHelper;


    @Override
    public UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        if (id != null) {
            User user1 = getUserModel(id);
            user1.setFirstName(userAddRequest.getFirstName());
            user1.setMiddleName(userAddRequest.getMiddleName());
            user1.setLastName(userAddRequest.getLastName());
            user1.setFullName(userHelper.getFullName(user1));
            user1.setAge(userAddRequest.getAge());
            user1.setEmail(userAddRequest.getEmail());
            user1.setOccupation(userAddRequest.getOccupation());
            user1.setSalary(userAddRequest.getSalary());
            user1.setAddress(userAddRequest.getAddress());
            nullAwareBeanUtilsBean.copyProperties(user1, userAddRequest);
            userRepository.save(user1);
            return modelMapper.map(user1, UserResponse.class);
        } else {
            User user = modelMapper.map(userAddRequest, User.class);
            user.setFullName(userHelper.getFullName(user));
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userRepository.save(user);
            return userResponse1;
        }
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
    public Workbook getAllUserByPaginationInExcel(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest) throws InvocationTargetException, IllegalAccessException {
        HashMap<String, List<UserDataExcel>> hashMap = new LinkedHashMap<>();
        Page<UserResponse> page = userRepository.getAllUserByPagination(filter, sort, pageRequest);
        List<UserResponse> list = page.getContent();
        List<UserDataExcel> list1 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (UserResponse response : list) {
                UserDataExcel userDataExcel = new UserDataExcel();
                nullAwareBeanUtilsBean.copyProperties(userDataExcel, response);
                list1.add(userDataExcel);
            }
        }
        return ExcelUtils.createWorkbookFromData(list, "UserByPagination");


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

}
