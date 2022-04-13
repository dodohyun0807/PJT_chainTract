package com.ssafy.chaintract.service;

import com.ssafy.chaintract.file.Encryption;
import com.ssafy.chaintract.smartcontract.SmartContractService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Base64;
import java.util.Random;

@SpringBootTest
public class EncryptTest {
//    @Autowired
//    Encryption encryption;

    @Autowired
    SmartContractService smartContractService;

    final String path = "C:\\imageUpload\\7cefb1bd-b309-4373-bf04-d32dfc570c00.pdf";

    @Test
    public void EncEqualsDecTest() throws Exception {
        byte[] plain = Encryption.getBytes(new File(path));

        String enc = Encryption.encrypt(plain);
        String dec = Encryption.decrypt(enc);

        String plainBase64 = new String(plain, "UTF-8");
        Assertions.assertEquals(plainBase64, dec);

        long rand = new Random().nextLong();
        if(rand < 0) {
            rand *= -1;
        }
        smartContractService.uploadContract(14L, enc);
        String encFound = smartContractService.verify(14L);
        Assertions.assertEquals(plainBase64, encFound);
    }
}
