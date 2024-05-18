package com.example.student.controller;


import com.example.student.VO.PageVO;
import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Record;
import com.example.student.form.CreateForm;
import com.example.student.service.RecordService;
import com.example.student.service.StudentService;
import com.example.student.util.ResultVOUtil;
import kotlin.ranges.CharRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

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
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;




    //创建数据
    @PostMapping("/create")
    public ResultVO create(@RequestBody CreateForm createForm){
        Boolean create = this.recordService.create(createForm);
        if(!create)
            return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

    //显示数据+分页
    @GetMapping("/studentlist/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        PageVO resultPage = this.recordService.studentList(page,size);
        return ResultVOUtil.success(resultPage);
    }

    //删除
    @GetMapping("/delete/{id}/{page}/{size}")
    public ResultVO delete(@PathVariable("id")Integer studentId,@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        Boolean result = this.recordService.delte(studentId);
        PageVO resultPage = this.recordService.studentList(page,size);
        return ResultVOUtil.success(resultPage);
    }

   /* @GetMapping("/update/{id}/{page}/{size}")
    public ResultVO update(@PathVariable("id")Integer studentId, @PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody CreateForm createForm){
        Boolean result = this.recordService.update(studentId,createForm);
        PageVO resultPage = this.recordService.studentList(page,size);
        return ResultVOUtil.success(resultPage);
    }*/

    @PostMapping("/update")
    public ResultVO update(@RequestBody CreateForm createForm){
        Boolean update = this.recordService.update(createForm);
        if(!update)
            return ResultVOUtil.fail();
        return ResultVOUtil.success(null);
    }

}

