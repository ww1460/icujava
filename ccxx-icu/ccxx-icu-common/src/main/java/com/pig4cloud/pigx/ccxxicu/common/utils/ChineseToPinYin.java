package com.pig4cloud.pigx.ccxxicu.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: pigx
 * @description: 汉字转换为拼音
 * @author: yyj
 * @create: 2019-09-06 10:58
 **/
public class ChineseToPinYin {

	private volatile static ChineseToPinYin chineseToPinYin;

	private ChineseToPinYin(){};

	/***
	 * 将汉字转成拼音(取首字母或全拼)
	 * @param hanZi
	 * @param full 是否全拼
	 * @return
	 */
	public static String HanZiToPinYin(String hanZi, boolean full) {
		/***
		 * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
		 * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
		 * ^[\u4E00-\u9FA5]+$ 匹配简体
		 */
		String regExp = "^[\u4E00-\u9FFF]+$";
		StringBuffer sb = new StringBuffer();
		if (hanZi == null || "".equals(hanZi.trim())) {
			return "";
		}
		String pinyin = "";
		for (int i = 0; i < hanZi.length(); i++) {
			char unit = hanZi.charAt(i);
			if (match(String.valueOf(unit), regExp))//是汉字，则转拼音
			{
				pinyin = ConvertSingleHanZi2Pinyin(unit).toUpperCase();
				if (full) {
					sb.append(pinyin);
				} else {
					sb.append(pinyin.charAt(0));
				}
			} else {
				sb.append(unit);
			}
		}
		return sb.toString();
	}

	/***
	 * 将单个汉字转成拼音
	 * @param hanZi
	 * @return
	 */
	private static String ConvertSingleHanZi2Pinyin(char hanZi) {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] res;
		StringBuffer sb = new StringBuffer();
		try {
			res = PinyinHelper.toHanyuPinyinStringArray(hanZi, outputFormat);
			sb.append(res[0]);//对于多音字，只用第一个拼音
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sb.toString();
	}

	/***
	 * @param str 源字符串
	 * @param regex 正则表达式
	 * @return 是否匹配
	 */
	public static boolean match(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}



}
