package com.example.projectmanagement.validate.response;

import com.example.projectmanagement.base.BaseDTO;
import com.example.projectmanagement.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends BaseDTO {
    private String departmentName;
    private String code;
    private String mobile;
    private String email;
    private Long salary;
    private String username;
    private String address;
    private String roleName;
}
