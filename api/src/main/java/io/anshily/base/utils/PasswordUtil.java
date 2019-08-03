package io.anshily.base.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;


public class PasswordUtil {

	/**
	 * JAVA6支持以下任意一种算法 PBEWITHMD5ANDDES PBEWITHMD5ANDTRIPLEDES
	 * PBEWITHSHAANDDESEDE PBEWITHSHA1ANDRC2_40 PBKDF2WITHHMACSHA1
	 * */

	/**
	 * 定义使用的算法为:PBEWITHMD5andDES算法
	 */
	public static final String ALGORITHM = "PBEWithMD5AndDES";//加密算法
	
	/**
	 * 对称加密：采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。
	 * 常用的对称加密有：DES(Data Encryption Standard)、IDEA、RC2、RC4、SKIPJACK、RC5、AES算法等 
	 * 所谓对称，就是采用这种加密方法的双方使用方式用同样的密钥进行加密和解密。
	 * 密钥是控制加密及解密过程的指令。
	 * 算法是一组规则，规定如何进行加密和解密。 
	 */
	public static final String Salt = "96385288";//密钥

	/**
	 * 定义迭代次数为1000次
	 */
	private static final int ITERATIONCOUNT = 1000;

	
	
	/**
	 * 获取加密算法中使用的盐值,解密中使用的盐值必须与加密中使用的相同才能完成操作. 盐长度必须为8字节
	 * 
	 * @return byte[] 盐值
	 * */
	public static byte[] getSalt() throws Exception {
		// 实例化安全随机数
		SecureRandom random = new SecureRandom();
		// 产出盐
		return random.generateSeed(8);
	}

	/**
	 * 
	 * @Title: getStaticSalt 
	 * @Description: TODO(
	 * PBE——Password-based encryption（基于密码加密）其特点在于口令由用户自己掌管，不借助任何物理媒体；
	 * 采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。是一种简便的对称加密方式。
	 * )  
	 * 
	 * PBE算法没有密钥（秘密[对称]密钥）的概念，把密码+盐+消息摘要迭代数当做密钥了
	 * @return  byte[] 返回类型 
	 * @author Administrator
	 * @throws
	 * @date Jun 15, 2015 5:05:14 PM 
	 * @version V1.0
	 */
	public static byte[] getStaticSalt() {
		// 产出盐
		return Salt.getBytes();
	}

	/**
	 * 根据PBE密码生成一把密钥
	 * 
	 * @param password
	 *            生成密钥时所使用的密码
	 * @return Key PBE算法密钥
	 * */
	private static Key getPBEKey(String password) {
		// 实例化使用的算法
		SecretKeyFactory keyFactory;
		SecretKey secretKey = null;
		try {
			keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			// 设置PBE密钥参数
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			// 生成密钥
			secretKey = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return secretKey;
	}

	/**
	 * 加密明文字符串
	 * 
	 * @param plaintext
	 *            待加密的明文字符串
	 * @param password
	 *            生成密钥时所使用的密码
	 * @param salt
	 *            盐值
	 * @return 加密后的密文字符串
	 * @throws Exception
	 */
	public static String encrypt(String plaintext, String password, byte[] salt) {

		Key key = getPBEKey(password);
		byte[] encipheredData = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATIONCOUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

			encipheredData = cipher.doFinal(plaintext.getBytes());
		} catch (Exception e) {
		}
		return bytesToHexString(encipheredData);
	}

	/**
	 * 解密密文字符串
	 * 
	 * @param ciphertext
	 *            待解密的密文字符串
	 * @param password
	 *            生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
	 * @param salt
	 *            盐值(如需解密,该参数需要与加密时使用的一致)
	 * @return 解密后的明文字符串
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext, String password, byte[] salt) {

		Key key = getPBEKey(password);
		byte[] passDec = null;
		PBEParameterSpec parameterSpec = new PBEParameterSpec(getStaticSalt(), ITERATIONCOUNT);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

			passDec = cipher.doFinal(hexStringToBytes(ciphertext));
		}

		catch (Exception e) {
			// TODO: handle exception
		}
		return new String(passDec);
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param src
	 *            字节数组
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将十六进制字符串转换为字节数组
	 * 
	 * @param hexString
	 *            十六进制字符串
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) {
		int i=10;
		for (int j = 0; j < i; j++) {
			if((j)%3==0)
			{
				System.out.print("<br>");
			}
			else {
				System.out.print(j);
			}
		}
		System.out.print(-1%2==0);
		
		//待加密数据    
		String str = "admin";
		
		//设定的口令密码    
		String password = "111111";

		//org.jeecgframework.core.util.LogUtil.info("原文:" + str);
		//org.jeecgframework.core.util.LogUtil.info("密码:" + password);

		try {
			//初始化盐    
			byte[] salt = PasswordUtil.getStaticSalt();
			//加密数据    
			String ciphertext = PasswordUtil.encrypt(str, password, salt);
			System.out.println(ciphertext);
			//解密数据 
			String plaintext = PasswordUtil.decrypt(ciphertext, password, salt);
			System.out.println(plaintext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}