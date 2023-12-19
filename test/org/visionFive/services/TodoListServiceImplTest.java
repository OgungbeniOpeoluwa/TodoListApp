package org.visionFive.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.visionFive.data.model.Task;
import org.visionFive.data.repository.TaskRepository;
import org.visionFive.data.repository.TodoListRepository;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.dto.request.LoginRequest;
import org.visionFive.dto.request.RegisterRequest;
import org.visionFive.exception.InvalidDetailsException;
import org.visionFive.exception.InvalidRegistraionException;
import org.visionFive.exception.NoTaskAvailableForUserException;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TodoListServiceImplTest {
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    TodoListService todoListService;
     RegisterRequest registerRequest ;
      LoginRequest loginRequest;
    @BeforeEach
    public void doThisBeforeAnything(){
        taskRepository.deleteAll();
        todoListRepository.deleteAll();
    }
    @BeforeEach
    public void setup(){
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("philip");
        registerRequest.setPassword("password");

    }
    @BeforeEach
    public void loginSetup(){
        loginRequest = new LoginRequest();
        loginRequest.setUsername("philip");
        loginRequest.setPassword("password");



    }

    @Test
    public void registerTest(){
        todoListService.register(registerRequest);
        assertThrows(InvalidRegistraionException.class,()->todoListService.register(registerRequest));
    }
    @Test
    public void loginWithWrongPasswordThrowException(){
        todoListService.register(registerRequest);
        loginRequest.setUsername("philip");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidDetailsException.class,()-> todoListService.login(loginRequest));
    }
    @Test
    public void loginInWithWrongUsernameThrowException(){
        todoListService.register(registerRequest);
        loginRequest.setUsername("philips");
        loginRequest.setPassword("password");
        assertThrows(InvalidDetailsException.class,()-> todoListService.login(loginRequest));
    }
    @Test
    public void testThatWhenICreteATodoCountIncreaseByOne(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(17);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(11);
        dueDate.setMinutes(0);
        DataRequest dataRequest = new DataRequest("ope",dueDate);
        todoListService.create("philip",dataRequest);
        assertEquals(1,taskRepository.count());

    }
    @Test
    public void findAllTaskForThisToday(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(18);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(12);
        dueDate.setMinutes(0);
        DataRequest dataRequest = new DataRequest("ope",dueDate,date);
        todoListService.create("philip",dataRequest);
        List<Task> philipTask = todoListService.viewADayTodos("philip",date);
        assertEquals(1,philipTask.size());

    }
    @Test
    public void createTodoForPhilipAndDelightedFindAllTodoForPhilip(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);;
        registerRequest.setUsername("delighted");
        registerRequest.setPassword("ope");
        todoListService.register(registerRequest);
        loginRequest.setUsername("delighted");
        loginRequest.setPassword("ope");
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(18);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(5);
        dueDate.setMinutes(0);
        DataRequest dataRequest = new DataRequest("ope",dueDate,date);
        Date dates  = new Date();
        dates.setYear(2023);
        dates.setMonth(12);
        dates.setDay(19);
        DueDate dueDates = new DueDate();
        dueDates.setDate(dates);
        dueDates.setHour(11);
        dueDates.setMinutes(0);
        DataRequest dataRequests = new DataRequest("baby",dueDates);
        todoListService.create("philip",dataRequest);
        todoListService.create("delighted",dataRequests);
        List<Task> philipTask = todoListService.viewADayTodos("delighted",dates);
        assertEquals(1,philipTask.size());
    }
    @Test
    public void viewAParticularTaskForPhilipTest(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(19);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(10);
        dueDate.setMinutes(30);
        DataRequest dataRequest = new DataRequest("ope",dueDate);
        todoListService.create("philip",dataRequest);
        todoListService.findTask("philip","ope",date);
        assertThrows(InvalidDetailsException.class,()->todoListService.findTask("philip","name",date));
    }
    @Test
    public void testThaWhenICreateATaskICanUpdateTheTaskMessage(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(18);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(8);
        dueDate.setMinutes(35);
        DataRequest dataRequest = new DataRequest("ope",dueDate,date);
        todoListService.create("philip",dataRequest);
        todoListService.update("philip","ope","fix my bugs",date);
        Task task = todoListService.findTask("philip","fix my bugs",date);
        System.out.println(task.getLocalDate());
        assertNotNull(task.getMessage());
    }
    @Test
    public void testThatWhenICreateATaskAndSetADueDateICanUpdateTheDueDate(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date  = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(19);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(11);
        dueDate.setMinutes(0);
        DataRequest dataRequest = new DataRequest("ope",dueDate);
        todoListService.create("philip",dataRequest);
        Date dates  = new Date();
        dates.setYear(2024);
        dates.setMonth(10);
        dates.setDay(9);
        DueDate dueDates = new DueDate();
        dueDates.setDate(dates);
        dueDates.setHour(5);
        dueDates.setMinutes(30);
        todoListService.update("philip","ope",date,dueDates);
        LocalDateTime dateTime = LocalDateTime.of(2024,10,9,5,30);
        Task task = todoListService.findTask("philip","ope",date);
        assertEquals(dateTime,task.getDueDate());

    }
    @Test
    public void testThatWhenICreateTaskICanDeleteTask(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2024);
        date.setMonth(1);
        date.setDay(23);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(12);
        dueDate.setMinutes(30);
        DataRequest dataRequest = new DataRequest("Fixed my bugs",dueDate);
        todoListService.create("philip",dataRequest);
        todoListService.deleteAll("philip");
        assertThrows(NoTaskAvailableForUserException.class,()->todoListService.findTaskBelongingToTheUsername("philip"));
    }
    @Test
    public void testThatWhenICreateTwoTaskICanDeleteOne(){
        todoListService.register(registerRequest);
        todoListService.login(loginRequest);
        Date date = new Date();
        date.setYear(2023);
        date.setMonth(12);
        date.setDay(18);
        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setHour(4);
        dueDate.setMinutes(0);
        DataRequest dataRequest = new DataRequest("make my nails",dueDate,date);
        DataRequest dataRequests = new DataRequest("see my mom",dueDate);
        todoListService.create("philip",dataRequest);
        todoListService.create("philip",dataRequests);
        todoListService.deleteATask("philip","make my nails",date);
        assertThrows(InvalidDetailsException.class,()->todoListService.findTask("philip","make my nails",date));

    }




}