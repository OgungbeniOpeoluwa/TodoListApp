package org.visionFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.visionFive.dto.request.*;
import org.visionFive.dto.response.*;
import org.visionFive.exception.TodoListException;
import org.visionFive.services.TodoListService;

import static org.visionFive.util.Mapper.mapper;

@RestController
//@RequestMapping("api/user")
public class TodoController {
    private final TodoListService todoListService;
    @Autowired
    public TodoController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @CrossOrigin
    @GetMapping("/web-production-1e5e.up.railway.app")
    public String runApplication(){
        return "Application is up and running";
    }

    @PostMapping("/users")
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
    @PostMapping("/create")
    public ResponseEntity <?> create(@RequestBody CreateRequest createRequest){
        CreateResponse createResponse = new CreateResponse();
        try{
            todoListService.create(createRequest.getUsername(),mapper(createRequest));
            createResponse.setMessage("todo task has be created");
            return new ResponseEntity<>(new ApiResponse(createResponse,true),HttpStatus.CREATED);
        }
        catch(TodoListException exception){
            createResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(createResponse,false),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateRequest updateRequest){
        UpdateResponse updateResponse = new UpdateResponse();
        try{
            todoListService.update(updateRequest.getUsername(), updateRequest.getOldMessage(), updateRequest.getNewMessage(),updateRequest.getDate());
            updateResponse.setMessage("update successful");
            return  new ResponseEntity<>(new ApiResponse(updateResponse,true),HttpStatus.OK);
        }
        catch (TodoListException exception){
            updateResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(updateResponse,false),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewAll")
    public ResponseEntity<?> viewAllTask(@RequestBody AllTaskRequest allTaskRequest){
        AllTaskResponse allTaskResponse = new AllTaskResponse();
        try{
            allTaskResponse.setResponse(todoListService.findTaskBelongingToTheUsername(allTaskRequest.getUsername()));
            return new ResponseEntity<>(new ApiResponse(allTaskResponse,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException exception){
            allTaskResponse.setResponse(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(allTaskResponse,false),HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/viewADay")
    public ResponseEntity<?> viewALlTaskForAParticularDay(@RequestBody AllTaskForADayRequest task){
        AllDayTaskResponse response = new AllDayTaskResponse();
        try{
            response.setResponse(todoListService.viewADayTodos(task.getUsername(),task.getDate()));
            return new ResponseEntity<>(new ApiResponse(response,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException exception){
            response.setResponse(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(response,false),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewTask")
    public ResponseEntity<?> viewATask(@RequestBody ViewTaskRequest viewTaskRequest){
        ViewTaskResponse viewTaskResponse = new ViewTaskResponse();
        try{
            viewTaskResponse.setTask(todoListService.findTask(viewTaskRequest.getUsername(), viewTaskRequest.getMessage(),viewTaskRequest.getDate()));
            return new ResponseEntity<>(new ApiResponse(viewTaskResponse,true),HttpStatus.OK);
        }
        catch(TodoListException exception){
            viewTaskResponse.setTask(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(viewTaskRequest,false),HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/deleteAll/{username}")
    public ResponseEntity<?> deleteAllForUser(@PathVariable("username") String username){
        DeleteAllResponse deleteAllResponse = new DeleteAllResponse();
        try{
            todoListService.deleteAll(username);
            deleteAllResponse.setMessage("All Todo Has Been Deleted");
            return new ResponseEntity<>(new ApiResponse(deleteAllResponse,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException exception){
            deleteAllResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(deleteAllResponse,false),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteATask(@RequestBody DeleteRequest request){
        DeleteATaskResponse deleteATaskResponse = new DeleteATaskResponse();
        try{
            todoListService.deleteATask(request.getUsername(),request.getMessage(),request.getDate());
            deleteATaskResponse.setMessage("Task Has Been Deleted");
            return new ResponseEntity<>(new ApiResponse(deleteATaskResponse,true),HttpStatus.OK);
        }
        catch(TodoListException exception){
            deleteATaskResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(deleteATaskResponse,false),HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/updateDate")
    public ResponseEntity<?> updateDueDate(@RequestBody UpdateDueDateRequest update){
        UpdateDueDateResponse dueDateResponse = new UpdateDueDateResponse();
        try{
            todoListService.update(update.getUsername(),update.getTask(),update.getDate(),update.getDueDate());
            dueDateResponse.setMessage("Due Date Has Been Updated");
            return new ResponseEntity<>(new ApiResponse(dueDateResponse,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException exception){
            dueDateResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(dueDateResponse,false),HttpStatus.NOT_FOUND);
        }

    }
   @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable("username")String username){
        DeleteAccountResponse deleteResponse = new DeleteAccountResponse();
        try{
            todoListService.deleteTodoAccount(username);
            deleteResponse.setMessage("your account has been deleted");
            return new ResponseEntity<>(new ApiResponse(deleteResponse,true),HttpStatus.ACCEPTED);
        }
        catch(TodoListException exception){
            deleteResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(deleteResponse,false),HttpStatus.NOT_FOUND);
        }
   }



}
