package org.visionFive.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.visionFive.data.model.Task;
@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
}
