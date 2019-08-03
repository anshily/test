package io.anshily.base.utils;

import java.util.Random;

/**
 * 
 * @ClassName RandomNumber  
 *
 */
public class RandomNumber{
	
	/**
	 * 生成字母
	 * @param charCount
	 * @return
	 */
	public String getRandStr(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 26) + 'a');
			charValue += String.valueOf(c);
		}
		return charValue;
	}
	
	/**
	 * 生成数字
	 * @param charCount
	 * @return
	 */
	public String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}
	
	/**
	 * 从几开始到几
	 * @param from
	 * @param to
	 * @return
	 */
	public int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
	
	/**
	 * 测试部分
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception
	{
		RandomNumber randomNumber = new RandomNumber();
		System.out.println(randomNumber.getRandStr(6));
		System.out.println(randomNumber.getRandNum(6));
	}
}