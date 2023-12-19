package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.util.Date;
@Data
public class UpdateRequest {
    private String username;
    private String oldMessage;
    private String newMessage;
    private Date date;
}
