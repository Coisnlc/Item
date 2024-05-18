package com.example.student.service;

import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.form.LoginForm;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
public interface StudentService extends IService<Student> {

    public ResultVO login(LoginForm loginForm);

    public List<RecordVO> signlestudentList(Integer studentId);

}
