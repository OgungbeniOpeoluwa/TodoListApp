package org.visionFive.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.visionFive.data.model.Task;

public interface TaskRepository extends MongoRepository<Task,String> {
}
