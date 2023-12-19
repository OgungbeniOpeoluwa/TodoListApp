package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;
@Data
public class UpdateDueDateRequest {
    private String username;
    private String task;
    private Date date;
    private DueDate dueDate;
}
