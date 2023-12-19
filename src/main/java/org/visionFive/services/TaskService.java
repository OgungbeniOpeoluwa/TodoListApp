package org.visionFive.services;

import org.visionFive.data.model.Task;
import org.visionFive.dto.request.DataRequest;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.util.List;

public interface TaskService {
    void create(String todoId, DataRequest request);

    List <Task> findADayTask(String todoId, Date date);

    List<Task> findAllTaskBelongingToUser(String todoId);

    Task findTask(String todoId, String task, Date date);

    void update(String todoId, String oldMessage, String newMessage, Date date);

    void update(String todoId,String taskMessage, Date date, DueDate dueDate);

    void deleteAllTask(String todoId);

    void deleteATask(String todoId, String taskMessage, Date date);
}
