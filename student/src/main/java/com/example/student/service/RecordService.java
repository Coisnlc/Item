package com.example.student.service;

import com.example.student.VO.PageVO;
import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.student.form.CreateForm;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 */
public interface RecordService extends IService<Record> {

    public Boolean create(CreateForm createForm);

    public PageVO studentList(Integer page, Integer size);

    public Boolean delte(Integer id);

/*    public Boolean update(Integer id,CreateForm createForm);*/

    public Boolean update(CreateForm createForm);
}
