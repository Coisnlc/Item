package com.example.student.controller;


import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.form.LoginForm;
import com.example.student.service.StudentService;
import com.example.student.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public ResultVO login(LoginForm loginForm){

        ResultVO resultVO =this.studentService.login(loginForm);
        return  resultVO;

    }

    @GetMapping("/singleList/{id}")
    public ResultVO singleList(@PathVariable("id")Integer studentId){
        List<RecordVO> recordVOList = this.studentService.signlestudentList(studentId);
        return ResultVOUtil.success(recordVOList);
    }

}

