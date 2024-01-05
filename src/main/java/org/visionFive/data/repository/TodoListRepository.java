package org.visionFive.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.visionFive.data.model.TodoList;
import org.visionFive.services.TodoListService;
@Repository
public interface TodoListRepository extends MongoRepository<TodoList,String> {
    TodoList findByUsername(String username);
}
