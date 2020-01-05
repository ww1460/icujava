package com.pig4cloud.pigx.ccxxicu.service.impl.newlogin;

import com.pig4cloud.pigx.ccxxicu.CcxxIcuApplication;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = {CcxxIcuApplication.class}) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class TestMacRfidUserRelation {

	@Autowired
	HisDoctorsAdviceProjectService hisDoctorsAdviceProjectService;
	@Test
	public void testMapper(){
		System.out.println("--------------------");
		System.out.println(hisDoctorsAdviceProjectService.selectBatchNumber("20002").size());
	}
}
