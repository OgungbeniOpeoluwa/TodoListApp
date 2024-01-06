package org.visionFive.services;

import org.visionFive.data.model.Task;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.dto.request.LoginRequest;
import org.visionFive.dto.request.RegisterRequest;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.util.List;

public interface TodoListService {

    void register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);

    void create(String username, DataRequest dataRequest);

    List<Task> viewADayTodos(String username, Date date);

    List<Task> findTaskBelongingToTheUsername(String username);

    Task findTask(String username, String message,Date date);

    void update(String username, String oldMessage, String newMessage, Date date);

    void update(String username, String oldMessage, Date date, DueDate dueDate);

    void deleteAll(String username);

    void deleteATask(String username, String taskMessage, Date date);

    void deleteTodoAccount(String username);
}
