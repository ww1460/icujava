package com.pig4cloud.pigx.ccxxicu.service.nursing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputShow;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeOutputVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.IntakeTableVo;

import java.util.List;

/**
 * @program: pigx
 * @description: 出入量
 * @author: yyj
 * @create: 2019-10-23 17:17
 **/
public interface IntakeService extends IService<IntakeOutputRecord> {

	/**
	 * 出入量的新增
	 * @param record
	 * @return
	 */
	boolean add(IntakeOutputRecord record);



	/**
	 * 查询某关联数据
	 * @param intakeOutputRecord
	 * @return
	 */
	IntakeOutputRecord selectCorrelationValue(IntakeOutputRecord intakeOutputRecord);


	/**
	 * 出入量分页查询
	 * @param page
	 * @param intakeOutPutBo
	 * @return
	 */
	IPage<IntakeOutputVo> selectByPage(Page page, IntakeOutPutBo intakeOutPutBo);



	/**
	 * 查询护理记录单一 24小时
	 * @param nursingBo
	 * @return
	 */
	IntakeTableVo getReport(NursingBo nursingBo);


	/**
	 * 获取该患者的出入量数据(图标展示 24小时)
	 * @param nursingBo
	 * @return
	 */
	List<IntakeOutputShow> getDateShow(NursingBo nursingBo);





	/**
	 * 出入量修改
	 * @param intakeOutputRecord
	 * @return
	 */
	boolean updateRecord(IntakeOutputRecord intakeOutputRecord);

	/**
	 * 出入量删除
	 * @param intakeOutputRecord
	 * @return
	 */
	boolean delete(IntakeOutputRecord intakeOutputRecord);








}
