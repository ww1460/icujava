package com.pig4cloud.pigx.ccxxicu.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配
 */
@SuppressWarnings("AlibabaAvoidPatternCompileInMethod")
public class RegularMatchUtils {

	private volatile static RegularMatchUtils regularMatchUtils;
	private RegularMatchUtils(){};

	/**
	 * 正则匹配整数
	 */
	public static Integer getInteger(String ar) {

		//正则  整数
		Pattern c = Pattern.compile("(\\d+)");
		//匹配输入的字符串
		Matcher matcher = c.matcher(ar);

		Integer reg = null;
		//存在整数
		if (matcher.find()) {
			reg = Integer.parseInt(matcher.group(0));
		}
		return reg;
	}

	/**
	 * 正则匹配血压值
	 */
	public static String getBP(String ar) {

		Pattern p = Pattern.compile("(\\d+\\/\\d+)");//正则 小数
		Matcher matcher = p.matcher(ar);//匹配输入的字符串

		String reg = null;
		//存在整数
		if (matcher.find()) {
			reg = matcher.group(0);
		}
		return reg;
	}



	/**
	 * 正则匹配小数
	 */
	public static String getDouble(String ar) {

		Pattern pattern = Pattern.compile("(\\d+\\.\\d+)");//正则 小数
		Matcher matcher = pattern.matcher(ar);//匹配输入的字符串
		Pattern c = Pattern.compile("(\\d+)");//正则  整数
		Matcher matcher1 = c.matcher(ar);//匹配输入的字符串

		Double reg = null;

		if (matcher.find()) {

			reg = Double.parseDouble(matcher.group(0));

		} else if (matcher1.find()) {
			//存在整数
			reg = Double.parseDouble(matcher1.group(0));

		}

		if (reg!=null && reg%1 == 0) {

			return (int) Math.floor(reg)+"";

		}


		return reg+"";

	}
	/**
	 * 拆分血压
	 * @param ar
	 * @return
	 */
	public static List<String> splitBP(String ar) {

		boolean contains = ar.contains("/");

		//当包含时确定是数据录入
		if (contains) {

			//数据拆分
			String[] split = ar.split("/");

			if (split.length != 2) {

				return null;

			}
			Integer integer = RegularMatchUtils.getInteger(split[1]);
			split[1] =integer+"";

			return Arrays.asList(split);

		}
		return null;
	}
	/**
	 * 拆分冒号
	 * @param ar
	 * @return
	 */
	public static List<String> splitColon(String ar) {

		boolean contains = ar.contains(":");
		boolean contains1 = ar.contains("：");

		//当包含时确定是数据录入
		if (contains) {

			//数据拆分
			String[] split = ar.split(":");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}

		if (contains1) {

			//数据拆分
			String[] split = ar.split("：");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}


		return null;

	}

	/**
	 * 拆分等号
	 * @param ar
	 * @return
	 */
	public static List<String> splitEqual(String ar) {

		boolean contains = ar.contains("=");

		//当包含时确定是数据录入
		if (contains) {

			//数据拆分
			String[] split = ar.split("=");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}



		return null;

	}

	/**
	 * 拆分空格
	 * @param ar
	 * @return
	 */
	public static List<String> splitSpace(String ar) {

		boolean contains = ar.contains(" ");


		//当包含时确定是数据录入
		if (contains) {

			//数据拆分
			String[] split = ar.split(" ");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}

		return null;

	}

	/**
	 * 拆分逗号
	 * @param ar
	 * @return
	 */
	public static List<String> splitComma(String ar) {

		boolean contains = ar.contains(",");
		boolean contains1 = ar.contains("，");

		//当包含时确定是数据录入
		if (contains) {

			//数据拆分
			String[] split = ar.split(",");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}

		if (contains1) {

			//数据拆分
			String[] split = ar.split("，");

			if (split.length != 2) {

				return null;

			}

			return Arrays.asList(split);

		}


		return null;

	}

	/**
	 * 拆分分号
	 *
	 * @param input
	 * @return
	 */
	public static List<String> splitSemicolon(String input) {

		List<String> msg = new ArrayList<>();
		if (input.contains(";")) {

			String[] split = input.split(";");

			Arrays.asList(split).forEach(ar ->{
				if (ar.contains("；")) {

					String[] split1 = ar.split("；");
					msg.addAll(Arrays.asList(split1));

				} else {
					msg.add(ar);
				}
			});
		}

		return msg;
	}







}
