package com.example.student.form;

import lombok.Data;

@Data
public class CreateForm {
    private Integer studentid;
    private String name;
    private Integer score;
    private String phone;
    private Integer managerid;
    private String university;
    private String createtime;
}
