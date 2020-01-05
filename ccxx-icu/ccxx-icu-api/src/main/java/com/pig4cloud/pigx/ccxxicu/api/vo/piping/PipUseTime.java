package com.pig4cloud.pigx.ccxxicu.api.vo.piping;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: pigx
 * @description: 患者开始使用管路的时间
 * @author: yyj
 * @create: 2019-10-21 16:18
 **/
@Data
public class PipUseTime {

	private LocalDateTime startTime;

	private String pipName;


}
