package com.example.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.student.VO.RecordVO;
import com.example.student.VO.ResultVO;
import com.example.student.entity.Record;
import com.example.student.entity.Student;
import com.example.student.form.LoginForm;
import com.example.student.mapper.StudentMapper;
import com.example.student.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.mapper.Mapper;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.ABICodecException;
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
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    public final String configFile = "src/main/resources/config-example.toml";

    @Autowired
    private  StudentMapper studentMapper;

    @Override
    public ResultVO login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",loginForm.getPhone());
        Student student =this.studentMapper.selectOne(queryWrapper);
        ResultVO resultVO = new ResultVO();
        if(student == null){
            resultVO.setCode(-1);
        }else{
            //2.验证密码是否相同
            if(!student.getPassword().equals(loginForm.getPassword())){
                resultVO.setCode(-2);
            }else{
                resultVO.setCode(0);
                resultVO.setData(student);
            }
        }
        return resultVO;

    }

    @Override
    public List<RecordVO> signlestudentList(Integer studentId) {


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

        List<Object> params2 = new ArrayList<>();
        params2.add(Integer.toString(studentId));

        try {
            TransactionResponse transactionResponse2 =transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "getRecord", params2);
        } catch (ABICodecException e) {
            throw new RuntimeException(e);
        } catch (TransactionBaseException e) {
            throw new RuntimeException(e);
        }


        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid",studentId);
        List<Student> studentList=this.studentMapper.selectList(queryWrapper);
        List<RecordVO> recordVOList = new ArrayList<>();
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
        return recordVOList;
    }
}
