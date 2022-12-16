package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.common.config.constant.MessageConstant;
import com.example.auth.common.config.exception.NotFoundException;
import com.example.auth.decorator.*;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;



    @Override
    public UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        if (id != null) {
            User user1 = getUserModel(id);
            user1.setFirstName(userAddRequest.getFirstName());
            user1.setMiddleName(userAddRequest.getMiddleName());
            user1.setLastName(userAddRequest.getLastName());
            user1.setFullName();
            user1.setAge(userAddRequest.getAge());
            user1.setEmail(userAddRequest.getEmail());
            user1.setOccupation(userAddRequest.getOccupation());
            user1.setSalary(userAddRequest.getSalary());
            user1.setAddress(userAddRequest.getAddress());

            UserResponse userResponse1 = modelMapper.map(userAddRequest, UserResponse.class);
            nullAwareBeanUtilsBean.copyProperties(userResponse1, user1);
            userRepository.save(user1);
            return userResponse1;
        } else {
            User user = modelMapper.map(userAddRequest, User.class);
            user.setFullName();
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userRepository.save(user);
            return userResponse1;
        }
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAllBySoftDeleteFalse();
        List<UserResponse> userResponseList = new ArrayList<>();
        log.info("user:{}", users);
        users.forEach(user -> {
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse1);
        });

        return userResponseList;
    }

    @Override
    public UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException {
        User user = getUserModel(id);
       return modelMapper.map(user,UserResponse.class);
    }


    @Override
    public void deleteUser(String id) {
        User user = getUserModel(id);
        user.setSoftDelete(true);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getUserByAge(UserFilter userFilter) {
        return userRepository.getByFilterAndSoftDeleteFalse(userFilter);
    }

    @Override
    public List<UserAggregationResponse> getUserBySalary(UserFilter userFilter) {
        return userRepository.getUserByAggregation(userFilter);
    }


    @SneakyThrows
    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }



    boolean validNumber(String number) {
        Pattern p = Pattern.compile("^\\d{10}$");

        Matcher m = p.matcher(number);
        return (m.matches());
    }

    boolean validZip(String zip) throws RuntimeException {
        return zip.matches("^[0-9]{5}(?:-[0-9]{4})?$\n");
    }

    boolean validAge(String age) throws RuntimeException {
        return age.matches("^(0?[1-9]|[1-9][0-9]|[1][1-9][1-9]|200)$");
    }

    boolean regexEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


}
