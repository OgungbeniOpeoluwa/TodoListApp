package org.visionFive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.visionFive.controller.TodoController;
import org.visionFive.data.repository.TodoListRepository;
import org.visionFive.dto.request.UpdateRequest;
import org.visionFive.services.TodoListServiceImpl;
import org.visionFive.util.Date;
@EnableMongoRepositories

@SpringBootApplication
public class Main {
    public static void main(String[] args) {


        SpringApplication.run(Main.class,args);
    }
}