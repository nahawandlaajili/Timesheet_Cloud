// timesheet-service/src/main/java/com/cloud/timesheet/model/Timesheet.java
package com.cloud.timesheet.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate date;

    private int hoursWorked;

    private String taskDescription;
}