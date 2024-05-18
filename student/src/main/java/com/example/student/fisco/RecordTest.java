package com.example.student.fisco;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RecordTest {

    public final String configFile = "src/main/resources/config-example.toml";

    @Test
    public void test() throws Exception {
        //初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        //获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(Integer.valueOf(1));
        //构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件的存放路径
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "src/main/resources/abi/", "");


        //调用合约创建方法
/*        String recordid="4";
        String name="Coisn";
        String phone="18551440282";
        String score="222";
        String university="SKD";

        List<Object> params =new ArrayList<>();
        params.add(recordid);
        params.add(name);
        params.add(phone);
        params.add(score);
        params.add(university);

        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "addRecord", params);

        List<Object> returnValuse =transactionResponse.getReturnObject();
        if(returnValuse !=null){
            for(Object value :returnValuse){
                System.out.println("上链返回值："+value.toString());
            }
        }*/


/*        //调用合约查询接口
        List<Object> params2 = new ArrayList<>();
        params2.add("1");
        TransactionResponse transactionResponse2 =transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "getRecord", params2);

        List<Object> returnValues2 =transactionResponse2.getReturnObject();
        if(returnValues2!=null){
            if(returnValues2.size()==4) {
                String name = (String) returnValues2.get(0);
                String phone = (String) returnValues2.get(1);
                String score = (String) returnValues2.get(2);
                String university = (String) returnValues2.get(3);
                System.out.println("name:" + name);
                System.out.println("phone:" + phone);
                System.out.println("score:" + score);
                System.out.println("university:" + university);
            }else{
                System.out.println("返回长度不正确");
            }
        }*/

        //调用合约删除接口
/*        List<Object> params3 = new ArrayList<>();
        params3.add("4");
        TransactionResponse transactionResponse2 = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "remove", params3);
        List<Object> returnValues2 = transactionResponse2.getReturnObject();
        if (returnValues2 != null) {
            for (Object value : returnValues2) {
                System.out.println("上链返回值：" + value.toString());
            }
        }*/

        String recordid = "1";
        String name = "Coisn";
        String phone = "18551440282";
        String score = "222";
        String university = "SKD";

        List<Object> params3 = new ArrayList<>();
        params3.add(recordid);
        params3.add(name);
        params3.add(phone);
        params3.add(score);
        params3.add(university);

        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("record", "0x38e8c07ef95142e739ff1836b94d058a1f517770", "update", params3);

        List<Object> returnValuse3 = transactionResponse.getReturnObject();
        if (returnValuse3 != null) {
            for (Object value : returnValuse3) {
                System.out.println("上链返回值：" + value.toString());
            }
        }
    }
}
