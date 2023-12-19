package org.visionFive.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
public class Task {
    @Id
    private  String id;
    private LocalDate localDate;
    private String message;
    private String todoId;
    private LocalDateTime dueDate;

}
