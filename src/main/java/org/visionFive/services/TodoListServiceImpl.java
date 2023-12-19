package org.visionFive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.visionFive.data.model.Task;
import org.visionFive.data.model.TodoList;
import org.visionFive.data.repository.TodoListRepository;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.dto.request.LoginRequest;
import org.visionFive.dto.request.RegisterRequest;
import org.visionFive.exception.InvalidDetailsException;
import org.visionFive.exception.InvalidRegistraionException;
import org.visionFive.exception.NoTaskAvailableForUserException;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;
import org.visionFive.util.HashPassword;
import org.visionFive.util.Mapper;

import java.time.LocalDate;
import java.util.List;

import static org.visionFive.util.Mapper.*;

@Service
public class TodoListServiceImpl implements TodoListService {
    private final TodoListRepository todoListRepository;

    public TodoListServiceImpl(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }
    @Autowired
    private TaskService taskService;

    @Override
    public void register(RegisterRequest registerRequest) {
        if(userExist(registerRequest.getUsername()))throw new InvalidRegistraionException(registerRequest.getUsername() +" already exist");
        TodoList todoList = Mapper.mapRegisterRegistration(registerRequest);

        todoListRepository.save(todoList);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        if(!(userExist(loginRequest.getUsername())))throw new InvalidDetailsException("Invalid details");
        TodoList todoList = todoListRepository.findByUsername(loginRequest.getUsername());
        mapLogin(todoList,loginRequest.getPassword());
        todoList.setLocked(false);
        todoListRepository.save(todoList);
    }

    @Override
    public void create(String username, DataRequest request) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        checkIfAccountIsLocked(username);
        TodoList todoList = todoListRepository.findByUsername(username);
        checkIfMessageExist(todoList.getId(),request.getMessage(),request.getLocalDate());
        taskService.create(todoList.getId(), request);
    }

    @Override
    public List<Task> viewADayTodos(String username, Date date) {
        TodoList todoList = todoListRepository.findByUsername(username);
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        checkIfAccountIsLocked(username);
        List <Task> task = taskService.findADayTask(todoList.getId(),date);
        if(task.isEmpty()) throw new NoTaskAvailableForUserException("No Task Available for that Date");
        return task;
    }

    @Override
    public List<Task> findTaskBelongingToTheUsername(String username) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        checkIfAccountIsLocked(username);
        List <Task> task = taskService.findAllTaskBelongingToUser(todoList.getId());
       if(task.isEmpty())throw new NoTaskAvailableForUserException("No Task Available For User");
       return task;
    }

    @Override
    public Task findTask(String username, String task,Date date) {
        TodoList todoList = todoListRepository.findByUsername(username);
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        checkIfAccountIsLocked(username);
        Task task1 = taskService.findTask(todoList.getId(),task,date);
        if(task1 == null) throw new InvalidDetailsException("Wrong Details") ;
        return task1;
    }

    @Override
    public void update(String username, String oldMessage, String newMessage, Date date) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        checkIfAccountIsLocked(username);
        checkIfMessageExist(todoList.getId(),newMessage,setLocalDate(date));
        taskService.update(todoList.getId(),oldMessage,newMessage,date);
    }

    @Override
    public void update(String username, String message, Date date, DueDate dueDate) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        checkIfAccountIsLocked(username);
        TodoList todoList = todoListRepository.findByUsername(username);
        taskService.update(todoList.getId(),message,date,dueDate);
    }

    @Override
    public void deleteAll(String username) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        checkIfAccountIsLocked(username);
        taskService.deleteAllTask(todoList.getId());
    }

    @Override
    public void deleteATask(String username, String taskMessage, Date date) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        TodoList todoList = todoListRepository.findByUsername(username);
        checkIfAccountIsLocked(username);
        taskService.deleteATask(todoList.getId(),taskMessage,date);

    }

    @Override
    public void deleteTodo(String username) {
        if(!(userExist(username))) throw new InvalidDetailsException(username + " doesn't exist");
        TodoList todoList  = todoListRepository.findByUsername(username);
        todoListRepository.delete(todoList);
    }

    private boolean userExist(String username){
        TodoList todoList = todoListRepository.findByUsername(username);
        return todoList != null;
    }
    private void checkIfAccountIsLocked(String username){
        TodoList todoList = todoListRepository.findByUsername(username);
        if(todoList.isLocked())throw new InvalidDetailsException(username + "Account Is locked");
    }
    private void checkIfMessageExist(String id, String message, LocalDate date){
        if(taskService.findTask(id,message,changeLocalDateToDate(date)) != null){
            throw new InvalidDetailsException("Task already exist");
        }

    }
}
