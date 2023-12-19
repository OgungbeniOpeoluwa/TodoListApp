package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.util.Date;
@Data
public class DeleteRequest {
    private String username;
    private String message;
    private Date date;
}
