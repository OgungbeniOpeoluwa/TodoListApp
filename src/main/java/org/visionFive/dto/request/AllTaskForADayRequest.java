package org.visionFive.dto.request;

import lombok.Data;
import org.visionFive.data.model.Task;
import org.visionFive.util.Date;

import java.util.List;

@Data
public class AllTaskForADayRequest {
    private String username;
    private Date date;
}
