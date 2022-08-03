package com.example.projectmanagement.validate.response;


import com.example.projectmanagement.base.BaseDTO;
import com.example.projectmanagement.base.BaseEntity;
import com.example.projectmanagement.entity.ProjectEntity;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutputTaskDTO extends BaseDTO {

    private String title;

    private OutputProjectDTO project;

    private Date startDate;

    private Date endDate;

    private boolean status;

    private EmployeeDTO employee;
}
