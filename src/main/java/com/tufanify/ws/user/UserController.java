package com.tufanify.ws.user;


import com.tufanify.ws.shared.GenericResponse;
import error.ApiError;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

//    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user){
        String username = user.getUsername();
        if(username == null || username.isEmpty()){
            ApiError error = new ApiError(400,"Validation error", "/api/1.0/users");
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("username","Username cannot be null");
            error.setValidationErrors(validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        userService.save(user);
        return ResponseEntity.ok(new GenericResponse("User Created"));
    }
}
