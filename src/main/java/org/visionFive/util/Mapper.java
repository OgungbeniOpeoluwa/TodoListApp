package org.visionFive.util;

import org.visionFive.data.model.Task;
import org.visionFive.data.model.TodoList;
import org.visionFive.dto.request.CreateRequest;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.dto.request.RegisterRequest;
import org.visionFive.exception.InvalidDetailsException;
import org.visionFive.exception.InvalidPasswwordException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static TodoList mapRegisterRegistration(RegisterRequest registerRequest) {
        TodoList todoList = new TodoList();
        todoList.setUsername(registerRequest.getUsername());
        if(isPassword(registerRequest.getPassword())) {
            String salt = HashPassword.getSaltValue();
            String hashPassword = HashPassword.securePassword(registerRequest.getPassword(), salt);
            String password = salt + hashPassword;
            todoList.setPassword(password);
        }
        return todoList;
    }

    public static boolean isPassword(String password){
        if(password.matches("^[A-Z].*${8,20}")) return true;
        throw new InvalidPasswwordException("Password is too weak,must start with a capital letter,and any character with at least 8 length");
    }

    public static void mapLogin(TodoList todoList, String password){
        String saltValue = getExitedPasswordSaltValue(todoList.getPassword());
        String passwords = clearSaltValueInPassword(todoList.getPassword());
        String securePassword = HashPassword.securePassword(password,saltValue);
        if(!(passwords.equalsIgnoreCase(securePassword))) throw new InvalidDetailsException("Invalid details");
    }
    public static Date changeLocalDateToDate(LocalDate localDate){
        Date date = new Date();
        date.setYear(localDate.getYear());
        date.setMonth(localDate.getMonthValue());
        date.setDay(localDate.getDayOfMonth());
        return date;
    }


    public static Task mapCreateTask(String todoId, DataRequest request) {
        Task task  = new Task();
        task.setMessage(request.getMessage());
        task.setLocalDate(request.getLocalDate());
        task.setTodoId(todoId);
        task.setDueDate(request.getDueDate());
        return task;
    }

    public static String getExitedPasswordSaltValue(String password){
        String result = "";
        for(int count = 0; count < HashPassword.getLength();count++){
            result+=password.charAt(count);
        }
        return result;
    }
    public static String clearSaltValueInPassword(String password){
        String result = "";
        for(int count = HashPassword.getLength(); count < password.length();count++){
            result+=password.charAt(count);
        }
        return result;
    }

    public static List<Task> mapFindATask(Date date,List<Task> allTask) {
        List <Task> allTasks = new ArrayList<>();
        LocalDate dates = setLocalDate(date);
        for(Task task: allTask){
            LocalDate localDate = task.getLocalDate();
            if(localDate.getYear()==dates.getYear()&&localDate.getMonthValue()==dates.getMonthValue()&&localDate.getDayOfMonth()==dates.getDayOfMonth()){
                allTasks.add(task);
            }
        }
        return allTasks;
    }
    public static DataRequest mapper(CreateRequest createRequest){
        DataRequest dataRequest = new DataRequest();
        dataRequest.setMessage(createRequest.getMessage());
        if(createRequest.getDate() != null) {
            LocalDate localDate = setLocalDate(createRequest.getDate());
            dataRequest.setLocalDate(localDate);
        }
        LocalDateTime localDateTime = setLocalDateTime(createRequest.getDueDate());
        dataRequest.setDueDate(localDateTime);
        return dataRequest;

    }

    public  static LocalDate setLocalDate(Date date){
        return LocalDate.of(date.getYear(),date.getMonth(), date.getDay());
    }
    public  static LocalDateTime setLocalDateTime(DueDate date){
        Date dates = date.getDate();
        return LocalDateTime.of(dates.getYear(),dates.getMonth(), dates.getDay(), date.getHour(),date.getMinutes());
    }



}
