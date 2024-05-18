package com.example.student.service;

import com.example.student.VO.ResultVO;
import com.example.student.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
public interface ManagerService extends IService<Manager> {
    public ResultVO login(LoginForm loginForm);


}
