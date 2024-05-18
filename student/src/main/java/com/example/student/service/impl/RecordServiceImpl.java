package com.example.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.student.VO.PageVO;
import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Manager;
import com.example.student.entity.Record;
import com.example.student.entity.Student;
import com.example.student.form.CreateForm;
import com.example.student.mapper.ManagerMapper;
import com.example.student.mapper.RecordMapper;
import com.example.student.mapper.StudentMapper;
import com.example.student.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.student.util.CommonUtil;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.abi.datatypes.Int;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2024-03-07
 *
 */


@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    public final String configFile = "src/main/resources/config-example.toml";


    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @Override  //插入数据
    public Boolean create(CreateForm createForm) {

        //初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        //获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        //构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件的存放路径
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor;
        try {
            transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",createForm.getPhone());
        Student student1 = this.studentMapper.selectOne(queryWrapper);
        if(student1 !=null) {
            return false;
        }

        QueryWrapper<Student> queryWrapperid = new QueryWrapper<>();
        queryWrapperid.eq("studentid",createForm.getStudentid());
        Student student2=this.studentMapper.selectOne(queryWrapperid);
        if(student2 !=null) {
            return false;
        }


        Student student = new Student();
        student.setStudentid(createForm.getStudentid());
        student.setUniversity(createForm.getUniversity());
        student.setName(createForm.getName());
        student.setPhone(createForm.getPhone());
        student.setScore(createForm.getScore());
        student.setCreatetime(CommonUtil.createData());
        student.setAge(18);
        student.setSex("Man");
        student.setPassword(createForm.getPhone());
        student.setManagerid(createForm.getManagerid());



        int insert = this.studentMapper.insert(student);
        // System.out.println(student.getId());
        //上链
        List<Object> params =new ArrayList<>();
        params.add(Integer.toString(student.getStudentid()));
        params.add(student.getName());
        params.add(student.getPhone()+createForm.getManagerid());
        params.add(Integer.toString(student.getScore()));
        params.add(student.getUniversity());

        try {
            TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "addRecord", params);
        } catch (ABICodecException e) {
            throw new RuntimeException(e);
        } catch (TransactionBaseException e) {
            throw new RuntimeException(e);
        }
/*        System.out.println(createForm.getManagerid());*/
        if (insert != 1) return false;
        //1.判断用户是否存在
        //1.1判断手机号是否存在
 /*       QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",createForm.getPhone());
        Student student = this.studentMapper.selectOne(queryWrapper);
        if(student ==null){
            return  false;
        }else{
            //1.2手机号和姓名是否一致
            if(!student.getName().equals(createForm.getName())){
                return false;
                //2.用户存在：查询学生id+新增数据
            }else{
                Record record = new Record();
                record.setStudentId(student.getId());
                record.setSearcherId(22);
                record.setDescriptionEnbystu("12312");
                record.setId(createForm.getStudentid());
                record.setAffirm(0);
                record.setRemarkEnbystu("123123");
                record.setCreatime(CommonUtil.createData());

                int insert = this.recordMapper.insert(record);
                if(insert !=1) return false;
            }
        }*/
        return true;


    }

    @Override
    public PageVO studentList(Integer page, Integer size) { //展示数据
/*        List<Record> recordList = this.recordMapper.selectList(null);
        List<RecordVO> recordVOList =new ArrayList<>();
        for (Record record : recordList){
            RecordVO recordVO = new RecordVO();
            Student student = this.studentMapper.selectById(record.getStudentId());
            Manager manager = this.managerMapper.selectById(record.getSearcherId());
            recordVO.setId(record.getId());
            recordVO.setCreatetime(record.getCreatime());
            recordVO.setName(student.getName());
            recordVO.setPhone(student.getPhone());
            recordVO.setScore(student.getScore());
            recordVO.setUniversity(student.getUniversity());
            recordVOList.add(recordVO);
        }*/
        Page<Student> studentPage = new Page<>(page,size);
        Page<Student> resultPage = this.studentMapper.selectPage(studentPage, null);
        List<Student> studentList=resultPage.getRecords();
        List<RecordVO> recordVOList =new ArrayList<>();


        for (Student student:studentList){
            RecordVO recordVO = new RecordVO();
            recordVO.setId(student.getStudentid());
            recordVO.setCreatetime(student.getCreatetime());
            recordVO.setName(student.getName());
            recordVO.setPhone(student.getPhone());
            recordVO.setScore(student.getScore());
            recordVO.setUniversity(student.getUniversity());
            recordVOList.add(recordVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(recordVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public Boolean delte(Integer id) {
        //初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        //获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        //构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件的存放路径
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor;
        try {
            transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Object> params3 = new ArrayList<>();
        params3.add(Integer.toString(id));
        TransactionResponse transactionResponse2 = null;
        try {
            transactionResponse2 = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "remove", params3);
        } catch (ABICodecException e) {
            throw new RuntimeException(e);
        } catch (TransactionBaseException e) {
            throw new RuntimeException(e);
        }
/*        List<Object> returnValues2 = transactionResponse2.getReturnObject();
        if (returnValues2 != null) {
            for (Object value : returnValues2) {
                System.out.println("上链返回值：" + value.toString());
            }
        }*/
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid",id);
        int delete = this.studentMapper.delete(queryWrapper);
        if(delete !=1) return false;
        return true;
    }

    @Override
    public Boolean update(CreateForm createForm) {
        BcosSDK sdk = BcosSDK.build(configFile);
        //获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        //构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件的存放路径
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor;
        try {
            transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Object> params3 = new ArrayList<>();
        params3.add(Integer.toString(createForm.getStudentid()));
        params3.add(createForm.getName());
        params3.add(createForm.getPhone()+createForm.getManagerid());
        params3.add(Integer.toString(createForm.getScore()));
        params3.add(createForm.getUniversity());

        try {
            TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "update", params3);
        } catch (ABICodecException e) {
            throw new RuntimeException(e);
        } catch (TransactionBaseException e) {
            throw new RuntimeException(e);
        }


        QueryWrapper<Student> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("studentid",createForm.getStudentid());

        Student student =new Student();
        student.setUniversity(createForm.getUniversity());
        student.setName(createForm.getName());
        student.setPhone(createForm.getPhone());
        student.setScore(createForm.getScore());
        student.setManagerid(createForm.getManagerid());
        student.setCreatetime(CommonUtil.createData());
        student.setAge(18);
        student.setSex("Man");
        student.setPassword(createForm.getPhone());
        student.setStudentid(createForm.getStudentid());

        int update=this.studentMapper.update(student,queryWrapper);

        if(update!=1) return false;

        return true;

    }

/*    @Override
    public Boolean update(Integer id,CreateForm createForm) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Student student = new Student();
        student.setId(createForm.getStudentid());
        student.setUniversity(createForm.getUniversity());
        student.setName(createForm.getName());
        student.setPhone(createForm.getPhone());
        student.setScore(createForm.getScore());
        student.setCreatetime(CommonUtil.createData());
        student.setAge(18);
        student.setSex("Man");
        student.setPassword(createForm.getPhone());

        int update =this.studentMapper.update(student,queryWrapper);

        if(update!=1)  return false;
        return true;
    }*/
}


//查找写在Record 接口里
//显示全部和新增数据写在 Student接口里


//点击搜索 跳出一个信息框 不点击时信息框 false 呈现 true
//如果一个人有多份信息那么直接通过学号来查找
//增查这样就算完成了
//还需要完成改和删除 这样CRUD就完成了


//下面完成分页

