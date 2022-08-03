package com.example.projectmanagement.validate.response;

import com.example.projectmanagement.base.BaseDTO;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OutputProjectDTO extends BaseDTO {

    private String title;

    private DepartmentDTO department;

    private Date startDate;

    private Date endDate;

    private boolean status;
}
