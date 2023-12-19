package org.visionFive.dto.request;

import lombok.*;
import org.visionFive.util.Date;
import org.visionFive.util.DueDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class DataRequest {
   private  String message;
   private LocalDate localDate = LocalDate.now();
   private LocalDateTime dueDate;


   public DataRequest(String message, DueDate dates){
       this.message = message;
       this.localDate = LocalDate.now();
       this.dueDate = LocalDateTime.of(dates.getDate().getYear(),dates.getDate().getMonth(),dates.getDate().getDay(), dates.getHour(), dates.getMinutes());

   }
   public DataRequest(String message,DueDate dates,Date date){this.message = message;
       this.localDate = LocalDate.of(date.getYear(),date.getMonth(),date.getDay());
       this.dueDate = LocalDateTime.of(dates.getDate().getYear(),dates.getDate().getMonth(),dates.getDate().getDay(), dates.getHour(), dates.getMinutes());
   }
   public  DataRequest(){

   }




}
