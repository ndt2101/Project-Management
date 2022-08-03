package com.example.projectmanagement.entity;

import com.example.projectmanagement.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProjectEntity extends BaseEntity {

    @Column
    private String title;

    @Column
    private Long departmentId;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @Column
    private boolean status;

    @OneToMany(mappedBy = "project")
    private Set<TaskEntity> tasks;
}
