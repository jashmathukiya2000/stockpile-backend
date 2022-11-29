package com.example.auth.controller;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("add")
    @RequestMapping(name = "addUser", value = "/users",method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody UserAddRequest userAddRequest) {
        try {
            return new ResponseEntity<>(userService.addUser(userAddRequest), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("All")
    @RequestMapping(name = "getAllUser", value = "/users",method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUser() throws UserCollectionException {
        List<User> user = userService.getAllUser();
        if (user.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return ResponseEntity.of(Optional.of(user));
    }


    @GetMapping("{id}")
    @RequestMapping(name = "getUserById", value = "/users", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("{id}")
    @RequestMapping(name = "updateUser", value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserAddRequest userAddRequest) {
        try {
            userService.updateUser(id, userAddRequest);
            return new ResponseEntity<>("updated user with id" + " " + id, HttpStatus.OK);

        } catch (ConstraintViolationException e) {
            return new   ResponseEntity<>(e.getMessage(),  HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (UserCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @RequestMapping(name = "deleteUser", value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("deleted user with id" + " " + id, HttpStatus.OK);
        } catch (UserCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}



