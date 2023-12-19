package org.visionFive.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.visionFive.data.model.Task;
import org.visionFive.data.repository.TaskRepository;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.exception.InvalidDetailsException;
import org.visionFive.exception.NoTaskAvailableForUserException;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.visionFive.util.Mapper.*;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public void create(String todoId, DataRequest request) {
       Task task = mapCreateTask(todoId,request);
        taskRepository.save(task);
    }

    @Override
    public List<Task> findADayTask(String id, Date date) {
        List <Task> allTask = findAllTaskBelongingToUser(id);
        return mapFindATask(date,allTask);
    }

    @Override
    public List<Task> findAllTaskBelongingToUser(String id) {
        List <Task> allTask = new ArrayList<>();
        for(Task task: taskRepository.findAll()){
            if(task.getTodoId().equals(id)){
                allTask.add(task);
            }
        }
        return allTask;
    }

    @Override
    public Task findTask(String id, String taskMessage, Date date) {
        List <Task> findADayTask = findADayTask(id,date);
        for(Task task : findADayTask){
            if(task.getMessage().equalsIgnoreCase(taskMessage))return task;
        }
        return null;
    }

    @Override
    public void update(String id, String oldMessage, String newMessage, Date date) {
        Task tasks = findTask(id,oldMessage,date);
        if(tasks == null)throw new NoTaskAvailableForUserException("Task not found");
        tasks.setMessage(newMessage);
        taskRepository.save(tasks);

    }

    @Override
    public void update(String id,String taskMessage, Date date, DueDate dueDate) {
        Task task = findTask(id,taskMessage,date);
        if(task == null)throw new NoTaskAvailableForUserException("Task not found");
        LocalDateTime localDateTime = setLocalDateTime(dueDate);
        task.setDueDate(localDateTime);
        taskRepository.save(task);


    }

    @Override
    public void deleteAllTask(String todoId) {
        List <Task> foundTask = findAllTaskBelongingToUser(todoId);
        if(foundTask.isEmpty())throw  new NoTaskAvailableForUserException("No Task Found");
        taskRepository.deleteAll(foundTask);
    }

    @Override
    public void deleteATask(String todoId, String taskMessage, Date date) {
        Task task = findTask(todoId,taskMessage,date);
        if(task == null)throw  new NoTaskAvailableForUserException("task Not Found");
        taskRepository.delete(task);
    }

}
