package com.ssafy.chaintract.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.file.Files;

public class Encryption{

    static String key = "ZCXVsfda2Fwordle";

    public static byte[] getBytes(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return fileContent;
    }

    public static String encrypt(byte[] plainText) throws Exception {
        SecretKey keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        //set iv as random 16byte
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);

        // Encryption
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

        int blockSize = 128; //block size
//        byte[] dataBytes = plainText.getBytes("UTF-8");

        //find fillChar & pad
        int plaintextLength = plainText.length;
        int fillChar = ((blockSize - (plaintextLength % blockSize)));
        plaintextLength += fillChar; //pad

        byte[] plaintext = new byte[plaintextLength];
        Arrays.fill(plaintext, (byte) fillChar);
        System.arraycopy(plainText, 0, plaintext, 0, plainText.length);

        //encrypt
        byte[] cipherBytes = cipher.doFinal(plaintext);

        //add iv to front of cipherBytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( iv );
        outputStream.write( cipherBytes );

        //encode into base64
        byte [] encryptedIvText = outputStream.toByteArray();
        return new String(Base64.getEncoder().encode(encryptedIvText), "UTF-8");
    }

    public static String decrypt(String encryptedIvText) throws Exception {
        //decode with base64 decoder
        byte [] encryptedIvTextBytes = Base64.getDecoder().decode(encryptedIvText);

        // Extract IV.
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);



        // Decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKey keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] aesdecode = cipher.doFinal(encryptedBytes);

        // unpad
        byte[] origin = new byte[aesdecode.length - (aesdecode[aesdecode.length - 1])];
        System.arraycopy(aesdecode, 0, origin, 0, origin.length);

        return new String(origin, "UTF-8");
    }

}
