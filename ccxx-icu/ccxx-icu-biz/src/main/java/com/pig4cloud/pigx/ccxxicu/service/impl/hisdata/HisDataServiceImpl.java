package com.pig4cloud.pigx.ccxxicu.service.impl.hisdata;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.HisDoctorsAdviceBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.*;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDeptVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDoctorsAdviceVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisNurseVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisPatientVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceExtRedisVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceExtVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.hisdata.HisDoctorsAdviceRedisVo;
import com.pig4cloud.pigx.ccxxicu.common.emums.DoctorsAdviceExtEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.HisDataInterfaceEnum;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.*;
import com.pig4cloud.pigx.ccxxicu.service.nurse.NurseService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class HisDataServiceImpl implements HisDataService {

	@Autowired
	private DeptCorrelationService deptCorrelationService;
	@Autowired
	private NurseService nurseService;
	@Autowired
	private HisPatientService hisPatientService;

	/**
	 * 医嘱
	 */
	@Autowired
	private  HisDoctorsAdviceService hisDoctorsAdviceService;
	/**
	 * 医嘱执行情况
	 */
	@Autowired
	private  HisDoctorsAdviceExtService hisDoctorsAdviceExtService;
	/**
	 * 患者
	 */
	@Autowired
	private PatientService patientService;
	//redis
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;



	/**
	 * 医嘱执行情况修改
	 * @return
	 */
	@Override
	public Boolean updateDoctorsAdviceExt(HisDoctorsAdviceExt hisDoctorsAdviceExt) {

		HisDoctorsAdviceExtVo hisDoctorsAdviceExtVo = new HisDoctorsAdviceExtVo();

		hisDoctorsAdviceExtVo.setExecuteTime(LocalDateTime.now());//执行时间
		/* 通过我们当前护士的id  查询护士对应的hisid是多少 */
//		Nurse nurse = nurseService.selectByUserId(SecurityUtils.getUser().getId() + "");
//		if (nurse.getHisNurseId()==null){
//			System.out.println("要求修改his数据时查询到当前护士没有 his id");
//			return false;
//		}
		hisDoctorsAdviceExtVo.setDb(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT_UPDATE_Db.getCode());//测试库
		hisDoctorsAdviceExtVo.setExecuteNurse("844F003A-6CC4-4A32-919A-A9432107A3F3"); //护士
		hisDoctorsAdviceExtVo.setExecuteType(DoctorsAdviceExtEnum.COMPLETE.getCode());//完成状态
		hisDoctorsAdviceExtVo.setHisDoctorsAdviceProjectId("PK0590000000006593871IIIIIIIIIIIIIII"); //医嘱id
		hisDoctorsAdviceExtVo.setHisDoctorsAdviceId("PK0590000000006593871IIIIIIIIIIIIIII"); //关联医嘱id
		hisDoctorsAdviceExtVo.setHisFZYExecInfoId("PK0370000000028667131IIIIIIIIIIIIIII"); //医嘱执行情况id
		hisDoctorsAdviceExtVo.setExecuteTime(LocalDateTime.now());//执行时间
		hisDoctorsAdviceExtVo.setHisPatientId("REG0260000000001065955IIIIIIIIIIIIII");  //患者id
		String http = deptCorrelationService.http(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT_UPDATE.getCode(), hisDoctorsAdviceExtVo);
			System.out.println("执行医嘱的内容-------------------"+http);
		return true;
	}


	/**
	 * 科室查询
	 * @return
	 */
	@Override
	public Boolean hisDept(HisDeptVo hisData) {

		/* 给his发的数据请求 */
		hisData.setDb("cs");  //科室标识

		String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_DEPT.getCode(), hisData);
		if (http ==null){
			return false;
		}else{
			System.out.println("接收到his科室数据--------"+http);
			return true;
		}
	}



	/**
	 * 护士查询
	 * @return
	 */
	@Override
	public Boolean hisNurse(HisNurseVo hisData) {
		/* 给his发的数据请求 */
		hisData.setDb(HisDataInterfaceEnum.NURSE_Db.getCode());  //护士标识
		List<DeptCorrelation> deptList = deptCorrelationService.listDept();
		if (CollectionUtils.isNotEmpty(deptList)){
			for (int i =0;i<deptList.size();i++){
				hisData.setHisDeptid(deptList.get(i).getHisDeptId());
			String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_NURSE.getCode(), hisData);
			if (http == null){
				return false;
			}else{
				System.out.println("护士信息："+deptList.get(i)+"科室的护士----"+http);
			}
		}
			return true;
		}else{
			return false;
		}

	}

	/**
	 * 患者查询
	 * @return
	 */
	@Override
	public Boolean hisPatient(HisPatientVo hisData) {
		/* 要接收数据时，需要先去his查询出患者 */


		List<DeptCorrelation> deptCorrelations = deptCorrelationService.listDept();
		if (CollectionUtils.isEmpty(deptCorrelations)){
			return false;
		}
		for (int j =0;j<deptCorrelations.size();j++){
			hisData.setHisDeptid(deptCorrelations.get(j).getHisDeptId());//his科室数据
			hisData.setDb(HisDataInterfaceEnum.PATIENT_Db.getCode()); //患者标识
			String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_PATIENT.getCode(), hisData);
			System.out.println("患信息"+http);
				if (StringUtils.isNotEmpty(http)){//当接收到的his数据为空时，想前台提示，his传输数据失败！！
					throw new ApiException(ResponseCode.HIS_PATIENT_FAIL.getCode(),ResponseCode.HIS_PATIENT_FAIL.getMessage());
				}
				/* 把字符串转出json格式 */
				List<HisPatient> hisPatients = JSONArray.parseArray(http, HisPatient.class);
				if (CollectionUtils.isEmpty(hisPatients)){
					return true;
				}

					for (int i = 0; i < hisPatients.size(); i++) {
						Boolean aBoolean = hisPatientService.hisPatientAdd(hisPatients.get(i));
						if (!aBoolean) {
							return false;
						}
					}

		}

		return true;
	}

	/**
	 * his医嘱查询，患者id查询时间
	 * @return
	 */
	@Override
	public Boolean hisDoctorsAdvice(String hisPatientId) {


		/*判断一下患者是否有数据，如果患者有数据时，用当前患者去查询医嘱，如果没有患者时查询当前科室所有在科患者*/
		HisDoctorsAdviceVo hisData =new HisDoctorsAdviceVo();
		/* 要接收数据时，需要先去his查询出医嘱内容 */

		hisData.setDb(HisDataInterfaceEnum.DOCTORS_ADVICE_Db.getCode()); //医嘱标识

		if (StringUtils.isNotEmpty(hisPatientId)){  //有患者id时
			hisData.setHisPatientId(hisPatientId);

		//	String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_DOCTORS_ADVICE.getCode(), hisData);
		//	System.out.println("医嘱内容-------------------"+http);
		//	List<HisDoctorsAdviceBo> list = JSONArray.parseArray(http, HisDoctorsAdviceBo.class);
		//	System.out.println("数据长度-----"+list.size());

			/**
			 * 模拟list数据
			 */
			List<HisDoctorsAdviceBo> list = new ArrayList<>();


			int i1 = (int) (1 + Math.random() * (10 - 1 + 1));
			for (int b=0;b<i1;b++){
				HisDoctorsAdviceBo hisDoctorsAdviceBo = new HisDoctorsAdviceBo();
				hisDoctorsAdviceBo.setHisDoctorsAdviceId("A+"+hisPatientId+b);
				hisDoctorsAdviceBo.setBatchNumber("A"+hisPatientId+b);
				hisDoctorsAdviceBo.setDoctorsAdviceTime(LocalDateTime.now());
				hisDoctorsAdviceBo.setType((b/2==1)?"1":"2");
				hisDoctorsAdviceBo.setHisPatientId(hisPatientId);
				hisDoctorsAdviceBo.setEmergency("1");
				List<HisDoctorsAdviceProject> projects = new ArrayList<>();
				for (int h=0;h<i1;h++){
					HisDoctorsAdviceProject hh = new HisDoctorsAdviceProject();
					hh.setBatchNumber("0000"+hisPatientId+b);
					hh.setHisDoctorsAdviceId("A+"+hisPatientId+b);
					hh.setHisDoctorsAdviceProjectId("P+"+hisPatientId+b+h);
					hh.setDoctorsAdviceProjectType((h/5==1)?null:"2");
					if (hh.getDoctorsAdviceProjectType()==null){

					}else{
					hh.setContent("使用  【"+h+"】号  药物");
					hh.setDrugUse("静脉注入");
					hh.setConsumption(h+1+"00");
					hh.setCompany("mg");
					}
					projects.add(hh);
				}
				hisDoctorsAdviceBo.setProjectList(projects);
				list.add(hisDoctorsAdviceBo);
			}





////////////////////////////////////////////////////    原逻辑   ///////////////////////////////////////////////////////
//			if (CollectionUtils.isNotEmpty(list)){
//				for (int i =0;i<list.size();i++){
//					if (list.get(i)!=null || CollectionUtils.isNotEmpty(list.get(i).getProjectList())){
//						list.get(i).setNum(true);
//						HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
//						hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
//						hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
//						hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
//						redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
//						Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(list.get(i));
//						if (!b){
//							return false;
//						}
//					}else{
//						System.out.println("数据接收不全"+list.get(i));
//					}
//				}
//			}else{
//				return true;
//			}
/////////////////////////////////////////////////    新内容   //////////////////////////////////////////////////////////

			if (CollectionUtils.isEmpty(list)) {//判断查询到的数据为空时不做处理
				System.out.println("当前患者没在his中医嘱");
			}{
			/*  判断当前患者是否有存在医嘱，如果有的情况下只执行修改和新增的数据，如果没有的情况下直接新增数据*/
				List<HisDoctorsAdvice> hisDoctorsAdvices = hisDoctorsAdviceService.hisPatientId(hisPatientId);
				//当前患者没有过医嘱数据时，将查询到的所有医嘱数据新增
				if (CollectionUtils.isEmpty(hisDoctorsAdvices)){

					for (int i =0;i<list.size();i++){
						if (list.get(i)!=null || CollectionUtils.isNotEmpty(list.get(i).getProjectList())){
							Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(list.get(i));
								if (!b){
									throw new ApiException(ResponseCode.HIS_DOCTORS_ADVICE_ADD_FALL.getCode(),ResponseCode.HIS_DOCTORS_ADVICE_ADD_FALL.getMessage());
								}
							//将当条数据的 医嘱id  新增时间  修改时间 保存到redis中
							HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
							hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
							hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
							hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
							hisDoctorsAdviceRedisVo.setState(list.get(i).getState());
							redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
							}else{
							throw new ApiException(ResponseCode.HIS_DOCTORS_ADVICE_PROJECT_FALL.getCode(),ResponseCode.HIS_DOCTORS_ADVICE_PROJECT_FALL.getMessage());
							}
					}
				}else{//当前所有医嘱
				/*对当前数据进行判断查询
				* 要考虑到后期定时器，设定定时器为10分钟，那么查询当前时间到20分钟之前的数据进行筛选
				*/

				LocalDateTime dateTime = LocalDateTime.now().minusMinutes(20);//当前时间减去20分钟后的时间
				/**
				 * 循环遍历list集合数据查询每条数据的 【下医嘱时间和修改时间是否在当前时间之内】
				 * 判断 【新增时间及修改时间】 是否大于【dateTime】20分钟之前的时间
				 */
				List<HisDoctorsAdvice> updateList = new ArrayList<>();//需要修改的医嘱
				List<HisDoctorsAdviceBo> insetList = new ArrayList<>();//需要新增的医嘱
				for (int i =0;i<list.size();i++){
					//判断每条医嘱的新增时间和修改时间
					//    当条任务的下医嘱时间是否在，【当前时间的 - 20分钟之后】,如果是，将当条医嘱新增    反之不做处理
					if (list.get(i).getDoctorsAdviceTime().isAfter(dateTime)){//在20分钟之前之后
						//判断这条数据是否存在于redis中，如果存在着不进行新增,通过his医嘱id查询当条数据
						Object o = redisTemplate.opsForValue().get(list.get(i).getHisDoctorsAdviceId());
						System.out.println(o);
						if (o==null){//如果查询redis数据为空时，当当前数据新增，同时添加到redis中
							insetList.add(list.get(i));
							//将当条数据的 医嘱id  新增时间  修改时间 保存到redis中
							HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
							hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
							hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
							hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
							hisDoctorsAdviceRedisVo.setState(list.get(i).getState());
							redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
						}

					}if (list.get(i).getUpdateTime() !=null&&list.get(i).getUpdateTime().isAfter(dateTime)){
						// 修改时间在【当前时间 - 20分钟之后】时，将当前医嘱存放到需要修改的医嘱当中，但是需要判断当前修改的医嘱是否已经存在于reids的新增中，
						// 如果是，证明当前数据是新增语句，不需要添加到修改当中。【医嘱的修改只能修改医嘱的状态 】

						Object obj = redisTemplate.opsForValue().get(list.get(i).getHisDoctorsAdviceId());//从redis查询当前任务之前的状态，是否在新增之中

						//当从redis查询出啦的数据为空时，或者和redis数据对此状态不一致时
						if (obj==null||!list.get(i).getState().equals(((HisDoctorsAdviceRedisVo) obj).getState())){
							HisDoctorsAdvice hisDoctorsAdvice =new HisDoctorsAdvice();
							BeanUtil.copyProperties(list.get(i),hisDoctorsAdvice);
							updateList.add(hisDoctorsAdvice);
							//将当条数据的 医嘱id  新增时间  修改时间 保存到redis中
							HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
							hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
							hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
							hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
							hisDoctorsAdviceRedisVo.setState(list.get(i).getState());
							redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
						}
					}

				}
				//处理完要新增的医嘱时，将这些医嘱
				for (int i =0;i<insetList.size();i++){
					if (insetList.get(i)!=null || CollectionUtils.isNotEmpty(insetList.get(i).getProjectList())){
						Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(insetList.get(i));
						if (!b){
							throw new ApiException(ResponseCode.OK.getCode(),"从his接收到的医嘱数据新增失败！！！！ ");
						}
					}else{
						System.out.println("数据接收不全"+list.get(i));
					}
				}

				// 处理要修改的医嘱内容 ，将医嘱id回填到需要修改的数据这种中,修改大医嘱只会修改状态，不修改医嘱内容
				updateList.forEach(e->{
					Boolean aBoolean = hisDoctorsAdviceService.updateHisDoctorsAdviceId(e);
					if (!aBoolean){
						throw new ApiException(ResponseCode.OK.getCode(),"从his接收到的医嘱数据修改失败！！！！ ");
					}
				});
				}
			}

		}else { //没有患者id时候
			List<Patient> patients = patientService.hisEnterPatient();
				for (int p =0;p<patients.size();p++){
					if (StringUtils.isNotEmpty(patients.get(p).getHisid())){
						hisData.setHisPatientId(patients.get(p).getHisid());
						String http = deptCorrelationService.http(HisDataInterfaceEnum.HIS_DOCTORS_ADVICE.getCode(), hisData);
						System.out.println("医嘱内容-------------------"+http);
						List<HisDoctorsAdviceBo> list = JSONArray.parseArray(http, HisDoctorsAdviceBo.class);
////////////////////////////////////////////////////   原逻辑    //////////////////////////////////////////////////////
//						if (CollectionUtils.isNotEmpty(list)){
//							for (int j =0;j<list.size();j++){
//								if (list.get(j)!=null || CollectionUtils.isNotEmpty(list.get(j).getProjectList())){
//									Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(list.get(j));
//									if (!b){
//										return false;
//									}
//								}else{
//									System.out.println("数据接收不全"+list.get(i));
//								}
//							}
//						}
////////////////////////////////////////////////////   新逻辑    //////////////////////////////////////////////////////

			if (CollectionUtils.isEmpty(list)) {//判断查询到的数据为空时不做处理
							System.out.println("当前患者没在his中医嘱");
						}{
							/*  判断当前患者是否有存在医嘱，如果有的情况下只执行修改和新增的数据，如果没有的情况下直接新增数据*/
							List<HisDoctorsAdvice> hisDoctorsAdvices = hisDoctorsAdviceService.hisPatientId(patients.get(p).getHisid());
							//当前患者没有过医嘱数据时，将查询到的所有医嘱数据新增
							if (CollectionUtils.isEmpty(hisDoctorsAdvices)){

								for (int i =0;i<list.size();i++){
									if (list.get(i)!=null || CollectionUtils.isNotEmpty(list.get(i).getProjectList())){
										Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(list.get(i));
										if (!b){
											throw new ApiException(ResponseCode.DOCTORS_ADVICE_HIS_ADD.getCode(),ResponseCode.DOCTORS_ADVICE_HIS_ADD.getMessage());
										}
									}else{
										throw new ApiException(ResponseCode.DOCTORS_ADVICE_HIS_NOT_SUB.getCode(),ResponseCode.DOCTORS_ADVICE_HIS_NOT_SUB.getMessage());
									}
								}
							}else{//当前所有医嘱
								/*对当前数据进行判断查询
								 * 要考虑到后期定时器，设定定时器为10分钟，那么查询当前时间到20分钟之前的数据进行筛选
								 */
								LocalDateTime dateTime = LocalDateTime.now().minusMinutes(20);//当前时间减去20分钟后的时间
								/**
								 * 循环遍历list集合数据查询每条数据的 【下医嘱时间和修改时间是否在当前时间之内】
								 * 判断 【新增时间及修改时间】 是否大于【dateTime】20分钟之前的时间
								 */
								List<HisDoctorsAdvice> updateList = new ArrayList<>();//需要修改的医嘱
								List<HisDoctorsAdviceBo> insetList = new ArrayList<>();//需要新增的医嘱
								for (int i =0;i<list.size();i++){
									//判断每条医嘱的新增时间和修改时间
									//    当条任务的下医嘱时间是否在，【当前时间的 - 20分钟之后】,如果是，将当条医嘱新增    反之不做处理
									if (list.get(i).getDoctorsAdviceTime().isAfter(dateTime)){//在20分钟之前之后
										//判断这条数据是否存在于redis中，如果存在着不进行新增,通过his医嘱id查询当条数据
										Object o = redisTemplate.opsForValue().get(list.get(i).getHisDoctorsAdviceId());
										if (o==null){//如果查询redis数据为空时，当当前数据新增，同时添加到redis中
											insetList.add(list.get(i));
											//将当条数据的 医嘱id  新增时间  修改时间 保存到redis中
											HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
											hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
											hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
											hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
											hisDoctorsAdviceRedisVo.setState(list.get(i).getState());
											redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
										}

									}if (list.get(i).getUpdateTime().isAfter(dateTime)){
										// 修改时间在【当前时间 - 20分钟之后】时，将当前医嘱存放到需要修改的医嘱当中，但是需要判断当前修改的医嘱是否已经存在于reids的新增中，
										// 如果是，证明当前数据是新增语句，不需要添加到修改当中。【医嘱的修改只能修改医嘱的状态 】

										Object obj = redisTemplate.opsForValue().get(list.get(i).getHisDoctorsAdviceId());//从redis查询当前任务之前的状态，是否在新增之中

										//当从redis查询出啦的数据为空时，或者和redis数据对此状态不一致时
										if (obj==null||!list.get(i).getState().equals(((HisDoctorsAdviceRedisVo) obj).getState())){
											HisDoctorsAdvice hisDoctorsAdvice =new HisDoctorsAdvice();
											BeanUtil.copyProperties(list.get(i),hisDoctorsAdvice);
											updateList.add(hisDoctorsAdvice);
											//将当条数据的 医嘱id  新增时间  修改时间 保存到redis中
											HisDoctorsAdviceRedisVo hisDoctorsAdviceRedisVo = new HisDoctorsAdviceRedisVo();
											hisDoctorsAdviceRedisVo.setIntsetTime(list.get(i).getDoctorsAdviceTime());//下医嘱时间
											hisDoctorsAdviceRedisVo.setHisDoctorsAdviceId(list.get(i).getHisDoctorsAdviceId());//his医嘱id
											hisDoctorsAdviceRedisVo.setUpdateTime(list.get(i).getUpdateTime());//修改时间
											hisDoctorsAdviceRedisVo.setState(list.get(i).getState());
											redisTemplate.opsForValue().set(hisDoctorsAdviceRedisVo.getHisDoctorsAdviceId(),hisDoctorsAdviceRedisVo,1, TimeUnit.DAYS);
										}
									}

								}
								//处理完要新增的医嘱时，将这些医嘱
								for (int i =0;i<insetList.size();i++){
									if (insetList.get(i)!=null || CollectionUtils.isNotEmpty(insetList.get(i).getProjectList())){
										Boolean b = hisDoctorsAdviceService.hisDoctorsAdvice(insetList.get(i));
										if (!b){
											throw new ApiException(ResponseCode.DOCTORS_ADVICE_HIS_ADD.getCode(),ResponseCode.DOCTORS_ADVICE_HIS_ADD.getMessage());
										}
									}else{
										throw new ApiException(ResponseCode.DOCTORS_ADVICE_HIS_NOT_SUB.getCode(),ResponseCode.DOCTORS_ADVICE_HIS_NOT_SUB.getMessage());
									}
								}

								// 处理要修改的医嘱内容 ，将医嘱id回填到需要修改的数据这种中,修改大医嘱只会修改状态，不修改医嘱内容
								updateList.forEach(e->{
									Boolean aBoolean = hisDoctorsAdviceService.updateHisDoctorsAdviceId(e);
									if (!aBoolean){
										throw new ApiException(ResponseCode.OK.getCode(),"从his接收到的医嘱数据修改失败！！！！ ");
									}
								});
							}
						}
				}
				}
		}
		return true;
	}

	/**
	 * 医嘱执行情况查询
	 * @return
	 */
	@Override
	public Boolean hisDoctorsAdviceExt(com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDoctorsAdviceExtVo hisData) {

		/*判断患者数据,是否有，有的情况下执行一次，如果没有执行次*/
		if (StringUtils.isNotEmpty(hisData.getHisPatientId())){//患者数据不为空时
			/* 通过患者id查询科室数据*/
			hisData.setDb(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT_Db.getCode()); //医嘱标识
			List<String> list1 = hisDoctorsAdviceService.stopDoctorsAdviceHisPatientId(hisData.getHisPatientId());
//			if (CollectionUtils.isNotEmpty(list1)){
//				hisData.setHisDoctorsAdviceIdList(list1);
//			}else{
//				throw new ApiException(ResponseCode.DOCTORS_ADVICE_IS_NULL.getCode(),ResponseCode.DOCTORS_ADVICE_IS_NULL.getMessage());
//			}
//			String http = deptCorrelationService.http(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT.getCode(), hisData);
//			System.out.println("执行医嘱的内容-------------------"+http);
//			List<HisDoctorsAdviceExt> list = JSONArray.parseArray(http, HisDoctorsAdviceExt.class);
//			System.out.println("集合数据-------------------"+list);
			List<HisDoctorsAdviceExt> list = new ArrayList<>();
			int i1 = (int) (1 + Math.random() * (10 - 1 + 1));
			for (int b=0;b<i1;b++){
				HisDoctorsAdviceExt hh = new HisDoctorsAdviceExt();
				hh.setHisFZYExecInfoId("hisExt11"+hisData.getHisPatientId()+b+1);
				hh.setHisDoctorsAdviceProjectId("his01");
				hh.setHisFZYExecInfoCreateTime(LocalDateTime.now());
				hh.setDoctorsAdviceExtId(SnowFlake.getId()+"");
				hh.setHisPatientId(hisData.getHisPatientId());
				hh.setHisDeptId("b1571d13-239d-4157-9261-6eadf907173a");
				hh.setDosage(b+1*100);
				hh.setCompany("ml");
				hh.setCreateTime(LocalDateTime.now());
				hh.setExecuteType(2);//执行中
				list.add(hh);
			}

			if (CollectionUtils.isEmpty(list)){//当查询到当前患者没有医嘱时，返回真，【当前患者可能还没有来得及时创建医嘱】
				return true;
			}
				/* 当传来的数据不为空时，判断新增时间 是否在【20】 之后 */
				LocalDateTime dateTime = LocalDateTime.now().minusMinutes(20);//20分钟之前的时间，简称【20】
				List<HisDoctorsAdviceExt> hisDoctorsAdviceExts = new ArrayList<>();//用来保存数据
				list.forEach(e->{//循环当前数据
					if (e.getHisFZYExecInfoCreateTime().isAfter(dateTime)&& redisTemplate.opsForValue().get(e.getHisFZYExecInfoId())==null){
						//当条医嘱的创建时间是否 【20】 之后 同时在redis中取不到数据时，保存新增当前数据
						hisDoctorsAdviceExts.add(e);
						HisDoctorsAdviceExtRedisVo hisDoctorsAdviceExtRedisVo = new HisDoctorsAdviceExtRedisVo();
						hisDoctorsAdviceExtRedisVo.setCreateTime(e.getHisFZYExecInfoCreateTime());
						hisDoctorsAdviceExtRedisVo.setHisDoctorsAdviceId(e.getHisDoctorsAdviceId());
						hisDoctorsAdviceExtRedisVo.setHisFZYExecInfoId(e.getHisFZYExecInfoId());
						redisTemplate.opsForValue().set(hisDoctorsAdviceExtRedisVo.getHisFZYExecInfoId(),hisDoctorsAdviceExtRedisVo,1,TimeUnit.DAYS);
						Boolean aBoolean = hisDoctorsAdviceExtService.hisAdd(e);
						if (!aBoolean){
							throw new ApiException(ResponseCode.HIS_DOCTORS_ADVICE_EXT_FALL.getCode(),ResponseCode.HIS_DOCTORS_ADVICE_EXT_FALL.getMessage());
						}
					}

				});


		}else{//患者数据为空时
			List<Patient> patients = patientService.hisEnterPatient(); // 查询所有患者
			if (CollectionUtils.isNotEmpty(patients)){ //没有患者是不需要去his获取医嘱，所有返回真
				return true;
			}
			for (int i =0;i<patients.size();i++){
				if (StringUtils.isNotEmpty(patients.get(i).getHisid())){//当前患者hisid不为

					hisData.setDb(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT_Db.getCode()); //医嘱标识
					List<String> lists = hisDoctorsAdviceService.stopDoctorsAdviceHisPatientId(patients.get(i).getHisid());
					if (CollectionUtils.isNotEmpty(lists)){
						hisData.setHisDoctorsAdviceIdList(lists);
					}else{
						throw new ApiException(ResponseCode.DOCTORS_ADVICE_IS_NULL.getCode(),ResponseCode.DOCTORS_ADVICE_IS_NULL.getMessage());
					}
					String http = deptCorrelationService.http(HisDataInterfaceEnum.DOCTORS_ADVICE_EXT.getCode(), hisData);
					System.out.println("执行医嘱的内容-------------------"+http);
					List<HisDoctorsAdviceExt> list = JSONArray.parseArray(http, HisDoctorsAdviceExt.class);
					if (CollectionUtils.isNotEmpty(list)){


						/* 当传来的数据不为空时，判断新增时间 是否在20分钟之前 */
						LocalDateTime dateTime = LocalDateTime.now().minusMinutes(20);//20分钟之前的时间，简称【20】
						List<HisDoctorsAdviceExt> hisDoctorsAdviceExts = new ArrayList<>();//用来保存数据
						list.forEach(e->{//循环当前数据
							if (e.getHisFZYExecInfoCreateTime().isAfter(dateTime)&& redisTemplate.opsForValue().get(e.getHisFZYExecInfoId())==null){
								//当条医嘱的创建时间是否 【20】 之后 同时在redis中取不到数据时，保存新增当前数据
								hisDoctorsAdviceExts.add(e);
								HisDoctorsAdviceExtRedisVo hisDoctorsAdviceExtRedisVo = new HisDoctorsAdviceExtRedisVo();
								hisDoctorsAdviceExtRedisVo.setCreateTime(e.getHisFZYExecInfoCreateTime());
								hisDoctorsAdviceExtRedisVo.setHisDoctorsAdviceId(e.getHisDoctorsAdviceId());
								hisDoctorsAdviceExtRedisVo.setHisFZYExecInfoId(e.getHisFZYExecInfoId());
								redisTemplate.opsForValue().set(hisDoctorsAdviceExtRedisVo.getHisFZYExecInfoId(),hisDoctorsAdviceExtRedisVo,1,TimeUnit.DAYS);
								Boolean aBoolean = hisDoctorsAdviceExtService.hisAdd(e);
								if (!aBoolean){
									throw new ApiException(ResponseCode.HIS_DOCTORS_ADVICE_EXT_FALL.getCode(),ResponseCode.HIS_DOCTORS_ADVICE_EXT_FALL.getMessage());
								}
							}

						});
					}
				}
			}
		}
		return true;
	}
}
