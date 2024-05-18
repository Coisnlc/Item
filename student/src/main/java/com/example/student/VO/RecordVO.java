package com.example.student.VO;

import lombok.Data;

@Data
public class RecordVO {
    //前端展示数据
    private Integer id;
    private String createtime;
    private String name;
    private String phone;
    private String university;
    private Integer score;
}
