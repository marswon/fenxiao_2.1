package com.kedang.fenxiao.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {
	private static String algorithm = "SHA1withRSA";// 算法名称,目前KMI接受绝大多数常用的签名算法
	public static final String KEY_ALGORITHM = "RSA";
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String PUBLIC_KEY = "RSAXsCpPublicKey";   
	public static final String PRIVATE_KEY = "RSAXsCpPrivateKey";
	public static final String SYS_PROPERTY_XIANGSHANG="sys-xiangshang.properties";
	    
	// 签名时需传入明文、私钥
	//der文件签名
	public static String sign(String text, String privatekey) throws Exception {
		byte[] privateKeyData = Base64.decodeBase64(privatekey);

		final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				privateKeyData);
		final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		final PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		final Signature signatureChecker = Signature.getInstance(algorithm);
		signatureChecker.initSign(privateKey);
		signatureChecker.update(text.getBytes(ENCODE_UTF8));

		byte[] cipherBytes = signatureChecker.sign();
		return Base64.encodeBase64String(cipherBytes);
	}

	// 验签时需传入明文、密文、公钥
	public static boolean verify(String text, String sign, String publickey)
			throws Exception {
		byte[] publicKeyData = Base64.decodeBase64(publickey);
		final X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(
				publicKeyData);
		final KeyFactory keyFactoryPublic = KeyFactory
				.getInstance(KEY_ALGORITHM);

		final PublicKey publicKey = keyFactoryPublic
				.generatePublic(keySpecPublic);
		final Signature signatureCheckerPublic = Signature
				.getInstance(algorithm);
		signatureCheckerPublic.initVerify(publicKey);
		signatureCheckerPublic.update(text.getBytes(ENCODE_UTF8));
		boolean result = signatureCheckerPublic.verify(Base64.decodeBase64(sign));
		return result;
	}

	
	//pem文件签名
	public static String signPem(byte[] data, String privateKey) throws Exception{
		// 解密由base64编码的私钥   
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey.getBytes()); 

        // 构造PKCS8EncodedKeySpec对象     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   


        // RSAConstants.KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   

        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 用私钥对信息生成数字签名 
        Signature signature = Signature.getInstance(algorithm);   
        signature.initSign(priKey);   
        signature.update(data);   
        return org.apache.commons.codec.binary.Base64.encodeBase64String(signature.sign());   

		
	}
	
	 /**
     * 取得私钥 
     *   
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPrivateKey(Map<String, Object> keyMap)   
            throws Exception {   
        Key key = (Key) keyMap.get(PRIVATE_KEY); 
        return Base64.encodeBase64String(key.getEncoded());   
    }   
    /** 
     * 取得公钥 
     *   
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPublicKey(Map<String, Object> keyMap)   
            throws Exception {   
        Key key = (Key) keyMap.get(PUBLIC_KEY);   

        return Base64.encodeBase64String(key.getEncoded());   
    }   

 public static Map<String, Object> initAnyPublicKeyPem(String keyPath) throws Exception {
 	String publicKeyPath=getKeyInfo(SYS_PROPERTY_XIANGSHANG,keyPath);
 	InputStream publicKeyinputStream = RSAUtil.class.getClassLoader().getResourceAsStream(publicKeyPath);  
 //	InputStream publicKeyinputStream = FileReader.class.getResourceAsStream(publicKeyPath); 
 	InputStreamReader publicKeyinputStreamReader= new InputStreamReader(publicKeyinputStream);
		BufferedReader publicKey = new BufferedReader(publicKeyinputStreamReader);	
		
	
		String strPublicKey = "";
		String line = "";
		
		while((line = publicKey.readLine()) != null){
			strPublicKey += line;
		}
	
		publicKey.close();

		// 私钥需要使用pkcs8格式的，公钥使用x509格式的
	
		String strPubKey = strPublicKey.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

		
		byte [] pubKeyByte = Base64.decodeBase64(strPubKey.getBytes());

		X509EncodedKeySpec 	pubKeySpec = new X509EncodedKeySpec(pubKeyByte);

		KeyFactory kf = KeyFactory.getInstance("RSA");

	
		PublicKey pubKey = kf.generatePublic(pubKeySpec);
     
     Map<String, Object> keyMap = new HashMap<String, Object>(2);   
  
     keyMap.put(PUBLIC_KEY, pubKey);   
    
     return keyMap;   
    	
    }
 public static Map<String, Object> initAnyPrivateKeyPem(String keyPath) throws Exception {
	 	//String privateKeyPath=getKeyInfo(SYS_PROPERTY_XIANGSHANG,keyPath);
    
//	 	InputStream privateKeyinputStream = RSAUtil.class.getClassLoader().getResourceAsStream("privkey.pem");  
//    	//InputStream privateKeyinputStream = FileReader.class.getResourceAsStream(privateKeyPath); 
//    	InputStreamReader privateKeyinputStreamReader= new InputStreamReader(privateKeyinputStream);
//    	BufferedReader privateKey = new BufferedReader(privateKeyinputStreamReader);
    	String strPrivateKey = "";
//    	String line = "";
//		while((line = privateKey.readLine()) != null){
//			strPrivateKey += line;
//		}
//	
//		privateKey.close();
	
		strPrivateKey = "-----BEGIN PRIVATE KEY-----"
+"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMvamDs9yZT5MpPg"
+"NMwlp/UqEituDR0GALH7HB32DFkyEBYvCgotmdh2hlCtdlVg6JPa+qB+77G90keM"
+"E536eqFqA+qGrr1Cvnv6odqx1eUeJ4MhNGsi5rNj+nE/wwRLBFL3aglps0lyWaC8"
+"j4t6HVeLFhkyuDrsGzsCGKYpCC0HAgMBAAECgYEAoo45fmQS7vyYXFsZPwF4IYPe"
+"4Ursogw7WbHIBgxCZI4LTeVMuDkMyRQanxLznbmdPOoNmRYfxTh9ChilPACU793n"
+"9cb3aLLBddaRLKlcS96Hx23GiwAdR73U+aiYEIgCGZrTqxkYXB17M0f40/Uw1f/w"
+"3rD9gcXqoH4ffiLMbSECQQDlbUMcFkZ84eoYT9WAHzezJ0s8bFf2lvQpZYwgFFoa"
+"//s6ltU2m82QpdhFvPHNcED+b0Rd9kErpjNIpd3i/LU1AkEA43cRx5qj/GvXOnDi"
+"mQeim46t3RJBWAtxHE4LXT0/TNPMthQNPgcs7VM367jQglk1bSFDc5unto9fbMuf"
+"LE4MywJASk9QfEluvUZDF2rMQTpbRSjGAqUo/JK6NKpSb5WH9dTRn5F5L73ZIC/H"
+"VBc1zo+8TWzmnPSE+UlUtQQcUUf+vQJBAM6ttf4DyNTmt91mDL63bUyiy99/Ytg5"
+"LUFmuHSz3fxUxkD63z1pD61kW/9XIj4OCLlr6/nziOQcSbx1F+AN2xkCQQC7R3cb"
+"eKIIVaBozDa+JEq+FebQav2QeMydQIzfRBlNTtGAdOKokVtPGMMkseC/NyF53g9i"
+"VGpyqhPwVRg1Q4Mc"
+"-----END PRIVATE KEY-----";
		// 私钥需要使用pkcs8格式的，公钥使用x509格式的
		String strPrivKey = strPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");				
	

		byte [] privKeyByte = Base64.decodeBase64(strPrivKey.getBytes());
	
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privKeyByte);
	

		KeyFactory kf = KeyFactory.getInstance("RSA");

		PrivateKey privKey = kf.generatePrivate(privKeySpec);

        
        Map<String, Object> keyMap = new HashMap<String, Object>(2);   

        keyMap.put(PRIVATE_KEY, privKey);   
       
       
        return keyMap; 
    }
    
    
    /**
     * 加密<br> 
     * 用私钥加密 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] encryptByPrivateKey(byte[] data, String key)   
            throws Exception {   
    	  // 对密钥解密   
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得私钥   
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);   

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);   

        return cipher.doFinal(data);   
    }   
    
    /**
     * 解密<br> 
     * 用公钥解密 
     *   
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */ 
    public static byte[] decryptByPublicKey(byte[] data, String key)   
            throws Exception {   
        // 对密钥解密 
        byte[] keyBytes = Base64.decodeBase64(key.getBytes());   

        // 取得公钥    
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   
        Key publicKey = keyFactory.generatePublic(x509KeySpec);   

        // 对数据解密 
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());   
        cipher.init(Cipher.DECRYPT_MODE, publicKey);   

        return cipher.doFinal(data);   
    }   
    /** *//** 
     * 校验数字签名 
     *   
     * @param data  加密数据 
     * @param publicKey  公钥 
     * @param sign  数字签名 
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *   
     */ 
    public static boolean verifypublic(byte[] data, String publicKey, String sign)   
            throws Exception {   

        // 解密由base64编码的公钥   
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());   
        // 构造X509EncodedKeySpec对象 
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);   


        // RSAConstants.KEY_ALGORITHM 指定的加密算法 
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);   

        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);   

        Signature signature = Signature.getInstance("SHA1withRSA");   
        signature.initVerify(pubKey);   
        signature.update(data);   

        // 验证签名是否正常  
        return signature.verify(Base64.decodeBase64(sign));
    }   
    
    public static String getKeyInfo(String filePath,String keyName){    
        InputStream proIn = RSAUtil.class.getClassLoader().getResourceAsStream(filePath);    
        Properties pro = new Properties();    
        try {    
            pro.load(proIn);    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
        //back = pro.getProperty(keyName);    
        return pro.get(keyName)==null?"":pro.get(keyName).toString();    
    }    

	
}
