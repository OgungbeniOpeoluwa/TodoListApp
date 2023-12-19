package org.visionFive.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TodoList {
    @Id
    private  String id;
    private String username;
    private String password;
    private boolean isLocked = true;



}
