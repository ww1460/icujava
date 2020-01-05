package com.pig4cloud.pigx.ccxxicu.schedule;


import com.pig4cloud.pigx.ccxxicu.api.entity.patient.IcuRecord;
import com.pig4cloud.pigx.ccxxicu.service.patient.IcuRecordService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/icuRecor" )
@Api(value = "icuRecor", tags = "每天24点，收集上个班的数据")
public class IcuRecorSchedule {


	private IcuRecordService icuRecordService;


	@Scheduled(cron = "0 0 9 * * ?")
	public void icuRecor(){
		System.out.println("进来了定时-*----------------------------------------------------------------");
		IcuRecord icuRecord = new IcuRecord();
		Boolean add = icuRecordService.add(icuRecord);
		if (!add){
			System.out.println(LocalDateTime.now()+"时间新增统计数据了！！！！！！！！!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}


	}


}
