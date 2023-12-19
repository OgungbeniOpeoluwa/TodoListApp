package org.visionFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.dto.request.LoginRequest;
import org.visionFive.dto.request.RegisterRequest;
import org.visionFive.dto.response.ApiResponse;
import org.visionFive.dto.response.LoginResponse;
import org.visionFive.dto.response.RegisterResponse;
import org.visionFive.exception.TodoListException;
import org.visionFive.services.TodoListService;
import org.visionFive.services.TodoListServiceImpl;
import org.visionFive.util.Date;

@RestController
@RequestMapping("api/request")
public class TodoController {
    private final TodoListService todoListService;
    @Autowired
    public TodoController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            todoListService.register(registerRequest);
            registerResponse.setMessage("Account has been created");
            return new ResponseEntity<>(new ApiResponse(registerResponse,true),HttpStatus.CREATED);
        }
        catch(TodoListException ex){
            registerResponse.setMessage(ex.getMessage());
            return  new ResponseEntity<>(new ApiResponse(registerResponse,false),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        try{
            todoListService.login(loginRequest);
            loginResponse.setMessage("You don login");
            return new ResponseEntity<>(new ApiResponse(loginResponse,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException ex){
            loginResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(loginResponse,false),HttpStatus.NOT_FOUND);
        }
    }
}
