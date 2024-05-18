package com.example.student.service;

import com.example.student.form.CreateForm;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public interface FiscoService {
    public Boolean create(CreateForm createForm);
}
