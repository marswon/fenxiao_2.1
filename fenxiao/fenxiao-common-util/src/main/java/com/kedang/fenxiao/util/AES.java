/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kedang.fenxiao.util;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jxd | NLC
 */
public class AES {
    public static String encrypt(String input, String key){
	byte[] encrypted = null;
	try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            
            byte[] tmp = input.getBytes("UTF-8");
            //0x00 方式填充自己补位
            int len = tmp.length;
            byte[] newBytes = new byte[len + 16 - len % 16];
            System.arraycopy(tmp, 0, newBytes, 0, len);
            System.out.println("newBytes="+Arrays.toString(newBytes));
            encrypted = cipher.doFinal(newBytes);
	}catch(Exception e){
            System.out.println(e.toString());
	}
	return new String(Base64.encodeBase64(encrypted));
    }
   
    public static String decrypt(String input, String key){
        //please do it yourself
        return "";
    }
    
    public static void main(String[] args) {
        String key = "abcdefghijklmnop";
        String data = "{\"hd1\":1,\"hd2\":2}";
        System.out.println("raw_data="+data);
        System.out.println("java_encoded="+AES.encrypt(data, key));
    }
    
}
