package com.cdeer.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新的3DES加密方法（兼容IOS）
 * 
 * @author jacklin
 * 
 */
public class ThreeDES {

	/**
	 * 创建日志对象
	 */
	private final Logger Log = LoggerFactory.getLogger(getClass());

	private static final String key = "cdeerSXED1987FADN@#$%abc";

	/**
	 * 加密
	 * 
	 * @param plainByte
	 *            明文
	 * @return 密文
	 */
	public byte[] encrypt(byte[] plainByte) {

		byte[] cipherByte = null;
		try {
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			cipherByte = cipher.doFinal(plainByte);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}
		return cipherByte;
	}

	/**
	 * 解密
	 * 
	 * @param cipherByte
	 *            密文
	 * @return 明文
	 */
	public byte[] decrypt(byte[] cipherByte) {

		byte[] decryptByte = null;
		try {
			// --解密的key
			DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);

			// --Chipher对象解密
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			decryptByte = cipher.doFinal(cipherByte);
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}

		return decryptByte;
	}

	public String encryptString(String sPlainText) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(sPlainText.getBytes());

		// BASE64Encoder encoder = new BASE64Encoder();
		// return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

		return Base64.encodeBytes(b).replaceAll("\r", "").replaceAll("\n", "");
	}

	public String decryptString(String sCipherText) throws Exception {
		// --通过base64,将字符串转成byte数组
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] bytesrc = decoder.decodeBuffer(src);
		byte[] bytesrc = Base64.decode(sCipherText);

		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}

	public static void main(String[] args) throws Exception {

		String src = "我们都是好孩子,爱haha，爱生活，爱手机...";

		ThreeDES method = new ThreeDES();
		System.out.println("原始数据[" + src.length() + "]：" + src);
		String strEnc = method.encryptString(src);
		System.out.println("加密数据[" + strEnc.length() + "]：" + strEnc);
		String strDec = method.decryptString(strEnc);
		System.out.println("解密数据[" + strDec.length() + "]：" + strDec);

		String testStr = "CetpkFhaJ/TiGG6Jt0SbljaPcCawBMo0NwMbY1dDd2SG2av9g/Vk2EeK27XXVAj9XyN2w2p/30c6McXw6SdCN1fpa1r7A6rz";
		System.out.println(method.decryptString(testStr));
	}

}
