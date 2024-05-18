package com.example.student.service.impl;

import com.example.student.VO.ResultVO;
import com.example.student.form.CreateForm;
import com.example.student.form.LoginForm;
import com.example.student.service.FiscoService;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionBaseException;

import java.util.ArrayList;
import java.util.List;

public class FiscoServiceImpl implements FiscoService {

    public final String configFile = "src/main/resources/config-example.toml";

    @Override
    public Boolean create(CreateForm createForm) {
        //初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        //获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        //构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件的存放路径
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = null;
        try {
            transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        List<Object> params =new ArrayList<>();

        params.add(Integer.toString(createForm.getStudentid()));
        params.add(createForm.getName());
        params.add(createForm.getPhone());
        params.add(Integer.toString(createForm.getScore()));

        TransactionResponse transactionResponse = null;
        try {
            transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "addRecord", params);
        } catch (ABICodecException e) {
            throw new RuntimeException(e);
        } catch (TransactionBaseException e) {
            throw new RuntimeException(e);
        }

        List<Object> returnValuse =transactionResponse.getReturnObject();
        if(returnValuse ==null){
            return false;
        }
        return  true;

    }
}
