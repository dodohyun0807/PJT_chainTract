package com.ssafy.chaintract.smartcontract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.ssafy.chaintract.domain.Contract;
import com.ssafy.chaintract.repository.ContractRepository;
import com.ssafy.chaintract.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

/**
 * 본 코드는 FundRaising 예제를 통해 call, transaction, receipt 을 활용해
 * 스마트 컨트랙트를 호출하는 실습으로, 후에 스마트 컨트랙트가 배포되면 본 코드를
 * 바탕으로 재작성할 것임.
 *
 * <p>
 * <a href="https://sabarada.tistory.com/21">참고 1</a>
 * <a href="http://docs.web3j.io/4.8.7/transactions/transactions_and_smart_contracts/">참고 2</a>
 */

@Component
public class SmartContractService {
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    TransactionRepository transactionRepository;

    // TODO: 블록체인  네트워크가 구현되는되면 주요 환경변수는 application,yml에서 정의할 것
    private String contract = "0xECbEac7251521C87fEDF33e4c2C95D7ED9323e86";
    private String from = "0xa6e96c314aa5e1a829e2eaa0be76ed1577900979";
    private String from_pwd = "0xed941d4faa94e8ac8a23c20cdd66bcb3f6d0448c62454e63665e3afba975f837";

    private Admin web3j = null;

    public SmartContractService()
    {
        web3j = Admin.build(new HttpService("https://rinkeby.infura.io/v3/4b187cac2a4541f58360bf4800d53b52")); // default server : http://localhost:8545
    }

    public Object ethCall(Function function) throws IOException {
        Transaction transaction = Transaction.createEthCallTransaction(from, contract,
                FunctionEncoder.encode(function));

        EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),
                function.getOutputParameters());

        System.out.println("ethCall.getResult() = " + ethCall.getResult());
        System.out.println("getValue = " + decode.get(0).getValue());
        System.out.println("getType = " + decode.get(0).getTypeAsString());

        return decode.get(0).getValue();
    }

    public EthSendTransaction ethSendRawTransaction(Function function) throws IOException, InterruptedException, ExecutionException, TransactionException {
        Credentials credentials = Credentials.create(from_pwd);

        TransactionManager txManager = new RawTransactionManager(web3j, credentials);

        EthSendTransaction transaction = txManager.sendTransaction(
                DefaultGasProvider.GAS_PRICE,
                new BigInteger("6721975"),
                contract,
                FunctionEncoder.encode(function),
                BigInteger.ZERO
        );

        return transaction;
    }

    public TransactionReceipt getReceipt(String transactionHash) throws IOException {

        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();

        if(transactionReceipt.getTransactionReceipt().isPresent()) {
            System.out.println("transactionReceipt.getResult().getContractAddress() = " +
                    transactionReceipt.getResult());
        } else {
            System.out.println("transaction complete not yet");
        }

        return transactionReceipt.getResult();
    }

    public boolean uploadContract(long contractId, String encrypted) throws IOException, ExecutionException, InterruptedException, TransactionException {
        Contract contract = contractRepository.findById(contractId).get();
        List<com.ssafy.chaintract.domain.Transaction> transactions = new ArrayList<>();
        String[] slicedEnc = encrypted.split("(?<=\\G.{5000})");
        boolean isOK = true;

        for(int i = 0; i < slicedEnc.length; ++i) {
            Function function = new Function("register",
                    Arrays.asList(new Uint256(contractId << 8 + i), new Utf8String(slicedEnc[i])),
                    Collections.emptyList());

            EthSendTransaction transaction = this.ethSendRawTransaction(function);

            if(transaction.hasError()) {
                isOK = false;
                break;
            }

            String txHash = transaction.getTransactionHash();
            TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
                    web3j,
                    TransactionManager.DEFAULT_POLLING_FREQUENCY,
                    TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
            TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(txHash);

            if(txReceipt.isStatusOK() == false) {
                isOK = false;
                break;
            }

            com.ssafy.chaintract.domain.Transaction tx = com.ssafy.chaintract.domain.Transaction.builder()
                    .contract(contract)
                    .txHash(txReceipt.getTransactionHash())
                    .build();
            transactions.add(tx);
        }

        transactionRepository.saveAll(transactions);
        return isOK;
    }

    public String verify(long contractId) throws IOException {
        Function function = new Function("verify",
                Arrays.asList(new Uint256(contractId)),
                Arrays.asList(new TypeReference<Utf8String>() {}));

        return (String)(this.ethCall(function));
    }
}