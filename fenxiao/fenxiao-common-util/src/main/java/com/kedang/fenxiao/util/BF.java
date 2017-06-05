/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kedang.fenxiao.util;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jxd | NLC
 */
public class BF {
    
    public static String bytes2Hex(byte buf[]) {
      StringBuffer sb = new StringBuffer(); 
            for (int i = 0; i < buf.length; i++) { 
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) { 
                    hex = '0' + hex; 
                }
                sb.append(hex.toUpperCase());
      }
      return sb.toString();	 
    }

    public static byte[] hex2Bytes(String hex) {
      if (hex.length() < 1)
        return null;
      byte[] result = new byte[hex.length() / 2];
      for (int i = 0; i < hex.length() / 2; i++) {
        int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
        int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
        result[i] = (byte) (high * 16 + low);
      }
      return result;
    }

    public static String encrypt(String input, String key, String iv){
      byte[] crypted = null;
      try{
                SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "BLOWFISH");
                AlgorithmParameterSpec siv = new IvParameterSpec(iv.getBytes());
                Cipher cipher = Cipher.getInstance("Blowfish/CBC/NoPadding");
                cipher.init(Cipher.ENCRYPT_MODE, skey, siv);

                 // 补位
                byte[] tmp = input.getBytes("UTF-8");
                int len = tmp.length;
                byte[] newBytes = new byte[len + 8 - len % 8];
                System.arraycopy(tmp, 0, newBytes, 0, len);
                System.out.println("newBytes="+Arrays.toString(newBytes));
                crypted = cipher.doFinal(newBytes);
      }catch(Exception e){
                System.out.println(e.toString());
      }
      return bytes2Hex(crypted);
    }
    
    public static String decrypt(String input, String key, String iv){
    	String plainText = "";
    	byte[] decrypted = null;
    	try {
    		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "BLOWFISH");
            AlgorithmParameterSpec siv = new IvParameterSpec(iv.getBytes());
            Cipher cipher = Cipher.getInstance("Blowfish/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skey, siv);
            //decrypt here as raw bytes
            decrypted = cipher.doFinal(hex2Bytes(input));
            plainText = new String(decrypted).trim();
      } catch (Exception e) {
        // TODO: handle exception
      }        
    	return plainText;
    }    
    
    
    public static void main(String[] args) {
        String key = "abcdefghijklmnop";
        String iv = "88888888";
        String data = "{\"bd1\":1,\"bd2\":2}";
        System.out.println("raw_data="+data);
        String xdata = BF.encrypt(data, key, iv);
        System.out.println("java_encoded="+xdata);
        String rdata = BF.decrypt(xdata, key, iv);
        System.out.println("java_decoded="+rdata);
    }
}
