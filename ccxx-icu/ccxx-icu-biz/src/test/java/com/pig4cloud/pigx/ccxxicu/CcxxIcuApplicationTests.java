package com.pig4cloud.pigx.ccxxicu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataInput;
import com.pig4cloud.pigx.ccxxicu.api.entity.collection.CollectionRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.newlogin.MacRfidUserRelation;
import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import com.pig4cloud.pigx.common.core.constant.enums.StatusChangeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CcxxIcuApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("-----------------");
	}

	@Test
	public void serializationMacRfidUserRelationTest() {
		String json = "";
		MacRfidUserRelation macRfidUserRelation = new MacRfidUserRelation();
		macRfidUserRelation.setId(1);
		macRfidUserRelation.setMacAddress("w12eqw313123");
		macRfidUserRelation.setNurseId("1001");
		macRfidUserRelation.setNurseName("lilili");
		macRfidUserRelation.setRfidId("1002");
		macRfidUserRelation.setRfidTime(LocalDateTime.now());
		macRfidUserRelation.setRfidType("N");
		macRfidUserRelation.setRfidTypeEnum(RfidTypeEnum.N);
		macRfidUserRelation.setTerminalIntranetIp("192.168.4.167");
		System.out.println(JSON.toJSON(macRfidUserRelation));
	}

	@Test
	public void DeserializationMacRfidUserRelationTest() {
		String json = "{\"nurseName\":\"lilili\",\"macAddress\":\"w12eqw313123\",\"rfidTypeEnum\":\"N\",\"nurseId\":\"1001\",\"terminalIntranetIp\":\"192.168.4.167\",\"id\":1,\"rfidTime\":1568338015285,\"rfidId\":\"1002\",\"rfidType\":\"N\"}\n";
		MacRfidUserRelation macRfidUserRelation = JSON.parseObject(json, MacRfidUserRelation.class);
		switch (macRfidUserRelation.getRfidTypeEnum()) {
			case M:
				System.out.println("M");
				break;
			case N:
				System.out.println("N");
				break;
		}
		System.out.println(macRfidUserRelation);
	}

	@Test
	public void DeserializationScenesDataTest() {
		String json = "{\"action\":\"medication\",\"ip\":\"192.168.4.141\",\"mac\":\"sdaldadad1123\",\"data\":[{\"type\":\"N\",\"status\":0,\"rfid\":\"1001\"},{\"type\":\"N\",\"status\":0,\"rfid\":\"1002\"},{\"type\":\"P\",\"status\":0,\"rfid\":\"2001\"},{\"type\":\"M\",\"status\":0,\"rfid\":\"3001\"}]}";
		ScenesDataInput scenesDataInput = JSON.parseObject(json, ScenesDataInput.class);
		System.out.println(scenesDataInput);
	}

	@Test
	public void testAndOperation() {
		String a = "111010";
		String b = "000110";
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < a.length(); i++) {
			String change = "" + a.charAt(i) + b.charAt(i);
			result.append(StatusChangeEnum.getFrom(change).getValue());
		}
		System.out.println(result.toString());
	}

	@Test
	public void testCharAt() {
		String statusChange = "14134";
		System.out.println(statusChange.charAt(3));
		System.out.println("3".equals("" + statusChange.charAt(3)));
	}

	@Test
	public void strTohex() throws Exception {
		String str = "BE12345678901234567";
		StringBuilder builder = new StringBuilder("");
		for (int i = 0; i < str.length(); i++) {
			char tmp = str.charAt(i);
			int tmpInt = 0;
			if ((tmp > 'A' && tmp < 'Z') || (tmp > 'a' && tmp < 'z')) {
				tmpInt = tmp;
			} else if (tmp >= '0' && tmp <= '9') {
				tmpInt = tmp - '0';
			} else {
				throw new Exception("参数中存在非法字符");
			}
			builder.append(Integer.toHexString(tmpInt));
		}
		System.out.println(builder.toString().trim().toLowerCase());
	}

	@Test
	public void hexToStr() throws Exception {
		String str = "4E12345678901234567,4E12345678901234567,4E12345678901234567";
		String[] rfids = str.split(",");
		StringBuilder builder = new StringBuilder("");
		for (String rfid : rfids) {
			String type = rfid.substring(0, 2);
			String temType = "" + (char) Integer.parseInt(type, 16);
			System.out.println("转换为的字母为：" + temType);
			RfidTypeEnum rfidTypeEnum = RfidTypeEnum.getFrom(temType);
			if (rfidTypeEnum == null) {
				throw new Exception("非法的Rfid类型");
			}
			builder.append(rfidTypeEnum.getType()).append("-").append(rfidTypeEnum.getType());
			for (int i = 2; i < rfid.length(); i++) {
				char tmp = rfid.charAt(i);
				if (tmp >= '0' && tmp <= '9') {
					builder.append(tmp);
				} else {
					throw new Exception("参数中存在非法字符");
				}
			}
			builder.append(",");
		}
		String result = builder.toString().trim().toUpperCase();
		System.out.println(result.substring(0, result.length() - 1));
	}

	@Test
	public void testCollectionRecord() {
		CollectionRecord record = new CollectionRecord();
		record.setIp("192.168.4.147");
		record.setMac("sdaldadad1123");
		record.setERfid("E1234567891234567");
		record.setPRfid("P1234567891234567");
		record.setTimestamp(new Date());
		Map<String, String> map = new HashMap<>();
		map.put("SYSTEM_DBP", "123");
		map.put("SYSTEM_SBP", "145");
		record.setData(map);
		System.out.println("cast 的json数据为:" + JSON.toJSON(record));
	}

	@Test
	public void getRFIDdata() {
		//
		String datastr = "bb022b30004e01020304050607089fffffbbc7c17ebb02220011cb30004e010203050607089fffffbbc7c17e";
		List<String> result = new ArrayList<>();
		Pattern rfidPattern = Pattern.compile("bb\\S*?7e");
		Matcher matcher = rfidPattern.matcher(datastr);
		while (matcher.find()) {
			System.out.println(matcher.group());
			result.add(matcher.group());
		}
		System.out.println(result);
		//return result;
	}


}
