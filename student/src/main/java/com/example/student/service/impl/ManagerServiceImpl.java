package com.example.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Manager;
import com.example.student.form.LoginForm;
import com.example.student.mapper.ManagerMapper;
import com.example.student.service.ManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;
    @Override
    public ResultVO login(LoginForm loginForm) {
        //1.判断用户是否存在
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",loginForm.getPhone());
        Manager manager = this.managerMapper.selectOne(queryWrapper);
        ResultVO resultVO =new ResultVO();
        if(manager == null){
            resultVO.setCode(-1);
        }else{
            //2.验证密码是否相同
            if(!manager.getPassword().equals(loginForm.getPassword())){
                resultVO.setCode(-2);
            }else{
                resultVO.setCode(0);
                resultVO.setData(manager);
            }
        }
        return resultVO;

    }
}
