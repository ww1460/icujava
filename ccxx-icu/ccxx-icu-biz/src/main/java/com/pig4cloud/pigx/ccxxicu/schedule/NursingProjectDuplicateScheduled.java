package com.pig4cloud.pigx.ccxxicu.schedule;

import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordDuplicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: pigx
 * @description: 护理项目记录的副本
 * @author: yyj
 * @create: 2019-09-12 17:04
 **/
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class NursingProjectDuplicateScheduled {

	@Autowired
	private ProjectRecordDuplicateService projectRecordDuplicateService;

	/**
	 * 将每个患者的每个项目每小时转存一条或无
	 */
	@Scheduled(cron = "0 0 * * * ?")
	private void nursingDuplicate() {

		projectRecordDuplicateService.saveRecord();

	}



}
