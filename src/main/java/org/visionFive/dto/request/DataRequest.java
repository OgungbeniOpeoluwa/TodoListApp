package org.visionFive.dto;

import lombok.*;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.nio.file.LinkOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor

public class DataRequest {
   private  String message;
   private Date date;
   private LocalDate localDate;
   private LocalDateTime dueDate;


   public DataRequest(String message, DueDate date){
       this.message = message;
       this.localDate = LocalDate.now();
       this.date = new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
       this.dueDate = LocalDateTime.of(date.getDate().getYear(),date.getDate().getMonth(),date.getDate().getDay(), date.getHour(), date.getMinutes());

   }
   public DataRequest(String message, Date date,DueDate dates){this.message = message;
       this.date = date;
       this.localDate = LocalDate.of(date.getYear(),date.getMonth(),date.getDay());
       this.dueDate = LocalDateTime.of(dates.getDate().getYear(),dates.getDate().getMonth(),dates.getDate().getDay(), dates.getHour(), dates.getMinutes());
   }



}
