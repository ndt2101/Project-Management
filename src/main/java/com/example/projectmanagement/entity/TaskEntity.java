package com.example.projectmanagement.entity;

import com.example.projectmanagement.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskEntity extends BaseEntity {

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private boolean status;

    @Column
    private Long employeeId;
}
