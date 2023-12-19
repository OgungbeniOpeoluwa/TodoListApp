package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.util.Date;
@Data
public class ViewTaskRequest {
    private String username;
    private String message;
    private Date date;
}
