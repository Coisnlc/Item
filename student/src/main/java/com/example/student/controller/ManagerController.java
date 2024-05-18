package com.example.student.controller;


import com.example.student.VO.ResultVO;
import com.example.student.form.LoginForm;
import com.example.student.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/login")
    public ResultVO login(LoginForm loginForm){
        ResultVO resultVO = this.managerService.login(loginForm);
        return resultVO;
    }
}

