package com.kedang.fenxiao.util;


import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * User: skyrain
 * Date: 14-1-4
 * Time: 下午1:49
 */
public class DESTools {

	private Key mKey;private byte[] DESkey;// 设置密钥，略去
	private byte[] DESIV = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB,
			(byte) 0xCD, (byte) 0xEF };// 设置向量，略去
	@SuppressWarnings("unused")
	private AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
	public DESTools() {
		try {
			this.DESkey = "12345678".getBytes("UTF-8");// 设置密钥
			DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
			iv = new IvParameterSpec(DESIV);// 设置向量
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
			mKey = keyFactory.generateSecret(keySpec);// 得到密钥对象
		} catch (Exception ex) {
		}
	}/**
	 * 加密String明文输入,String密文输出
	 *
	 * @param inputString
	 *            待加密的明文
	 * @return 加密后的字符串
	 */
	public String getEncString(String inputString) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String outputString = "";
		try {
			byteMing = inputString.getBytes("UTF8");
			byteMi = this.getEncCode(byteMing);
//			Base64.encodeBase64()
			byte[] temp = Base64.encodeBase64(byteMi);
			outputString = new String(temp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return outputString;
	}/**
	 * 解密 以String密文输入,String明文输出
	 *
	 * @param inputString
	 *            解密后的字符串
	 * @return 解密后的字符串
	 */
	public String getDecString(String inputString) {
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = Base64.decodeBase64(inputString.getBytes());
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 *
	 * @param byteS
	 *            待加密的字节码
	 * @return 加密后的字节码
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			// 得到加密对象Cipher
			SecureRandom sr = new SecureRandom();
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, mKey, sr);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 *
	 * @param byteD
	 *            带解密的字节码
	 * @return 解密后的字节码
	 */
	@SuppressWarnings("unused")
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			//得到加密对象Cipher
			cipher = Cipher.getInstance("DES");
			SecureRandom sr = new SecureRandom();
			cipher.init(Cipher.DECRYPT_MODE, mKey);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}
