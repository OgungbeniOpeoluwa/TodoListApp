package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

@Data
public class CreateRequest {
    private String username;
    private String message;
    private DueDate dueDate;
    private Date creationDate;
}
