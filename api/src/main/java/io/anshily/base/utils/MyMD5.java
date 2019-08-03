package io.anshily.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyMD5 {

	public static String myMD5(String strSrc) {
		MessageDigest md = null;
        String encStr = null;

        byte[] bt = strSrc.getBytes();
        try
        {
            md = MessageDigest.getInstance("MD5");
            md.update(bt);
            encStr = bytes2Hex(md.digest()); // to HexString
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("Invalid algorithm.");
        }
        return encStr;
	}
	/**转换为16进制字符**/
    public static String bytes2Hex(byte[] bts)
    {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++)
        {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1)
            {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
	public static void main(String[] args) {
		
		String  a = "'123','133','124','127','120','122',";
		
		a = a.replaceAll("'124',", "");
		
		System.out.println("--::"+a);
	}
}