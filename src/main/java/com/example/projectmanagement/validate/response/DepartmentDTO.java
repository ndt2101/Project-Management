package com.example.projectmanagement.validate.response;

import com.example.projectmanagement.base.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO extends BaseDTO {
    private String title;
    private String description;
}
