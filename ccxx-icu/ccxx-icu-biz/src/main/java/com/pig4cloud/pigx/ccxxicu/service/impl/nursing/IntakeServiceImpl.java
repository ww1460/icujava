package com.pig4cloud.pigx.ccxxicu.service.impl.nursing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.Project;
import com.pig4cloud.pigx.ccxxicu.api.vo.nursing.*;
import com.pig4cloud.pigx.ccxxicu.common.emums.InputDrugEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.IntakeOutputEnum;
import com.pig4cloud.pigx.ccxxicu.common.emums.ProjectTypeEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.mapper.nursing.IntakeMapper;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: pigx
 * @description: 出入量
 * @author: yyj
 * @create: 2019-10-23 17:18
 **/
@Service
public class IntakeServiceImpl extends ServiceImpl<IntakeMapper, IntakeOutputRecord> implements IntakeService {

	@Value("${report.startTime}")
	private Integer startHour;//开始的小时

	@Value("${report.endTime}")
	private Integer endHour;//结束的小时

	private final Integer first = 8;

	private final Integer second = 7;

	private final Integer third = 9;


	@Autowired
	private ProjectService projectService;


	/**
	 * 出入量新增
	 *
	 * @param record
	 * @return
	 */
	@Override
	public boolean add(IntakeOutputRecord record) {
		//如果创建时间为空  添加当前时间为创建时间
		if (record.getCreateTime() == null) {
			record.setCreateTime(LocalDateTime.now());
		}
		//获取平衡量
		Integer newEquilibriumAmount = this.getNewEquilibriumAmount(record);

		//当平衡量不为空时  将此次的出入量 加到平衡量中  为空则将出入量作为平衡量
		int num = (newEquilibriumAmount == null ? 0 : newEquilibriumAmount) + record.getIntakeOutputValue();
		//当前出入量的平衡量
		record.setEquilibriumAmount(num);
		record.setIntakeOutputId(SnowFlake.getId() + "");
		//规范数据  出量必须是负数 入量必须是正数
		if (record.getIntakeOutputType().equals(IntakeOutputEnum.OUTPUT.getCode())) {
			if (record.getIntakeOutputValue() > 0) {
				record.setIntakeOutputValue(-record.getIntakeOutputValue());
			}
		} else {

			if (record.getIntakeOutputValue() < 0) {
				record.setIntakeOutputValue(-record.getIntakeOutputValue());
			}
			//处理数据 把胶、晶体区分  只有入量需要处理
			List<InputDrugEnum> inputDrugEnums = Arrays.asList(InputDrugEnum.values());
			inputDrugEnums.forEach(ar -> {
				String description = ar.getDescription();
				if (record.getValueDescription().contains(description)) {
					record.setRemarks("胶体");
				}
			});
		}

		return this.save(record);
	}

	/**
	 * 用于新增时 查询当前的平衡量
	 * 根据患者和时间查询
	 *
	 * @return
	 */
	private Integer getNewEquilibriumAmount(IntakeOutputRecord record) {

		NursingBo nursingBo = new NursingBo();
		//将创建时间作为结束时间
		nursingBo.setEndTime(record.getCreateTime());

		NursingBo nursingBo1 = timeDisposal(nursingBo);

		nursingBo1.setEndTime(record.getCreateTime());

		nursingBo1.setPatientId(record.getPatientId());

		return baseMapper.getEquilibriumAmount(nursingBo);

	}

	/**
	 * 对时间进行处理
	 */
	private NursingBo timeDisposal(NursingBo nursingBo) {

		LocalDateTime endTime = nursingBo.getEndTime();

		if (endTime == null) {

			endTime = LocalDateTime.now();

		}

		int hour = endTime.getHour();

		LocalDateTime now = LocalDateTime.now();

		String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String format = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		//首先判断传来的日期是否为当前时间 因为当前时间和开始时间前后的区分
		if (formatNow.equals(format)) {
			nursingBo.setEndTime(now);

			//当前的小时数 大于开始的小时
			if (hour >= startHour) {

				LocalDateTime dateTime = endTime.withHour(startHour);
				nursingBo.setStartTime(dateTime);

			} else {
				LocalDateTime dateTime = endTime.minusDays(1);
				LocalDateTime dateTime1 = dateTime.withHour(startHour);
				nursingBo.setStartTime(dateTime1);
			}

		} else {
			LocalDateTime dateTime = endTime.withHour(startHour);
			nursingBo.setStartTime(dateTime);
			LocalDateTime dateTime1 = endTime.minusDays(-1);
			LocalDateTime dateTime2 = dateTime1.withHour(endHour);
			nursingBo.setEndTime(dateTime2);
		}
		return nursingBo;
	}


	/**
	 * 查询关联的数据
	 *
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public IntakeOutputRecord selectCorrelationValue(IntakeOutputRecord intakeOutputRecord) {
		return baseMapper.selectCorrelationValue(intakeOutputRecord);
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param intakeOutPutBo
	 * @return
	 */
	@Override
	public IPage<IntakeOutputVo> selectByPage(Page page, IntakeOutPutBo intakeOutPutBo) {
		return baseMapper.selectByPage(page, intakeOutPutBo);
	}



	/**
	 * 护理记录单一的出入量
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public IntakeTableVo getReport(NursingBo nursingBo) {
		NursingBo nursingBo1 = this.timeDisposal(nursingBo);
		List<IntakeOutputShow> dateShow = this.getDateShow(nursingBo1);

		List<Integer> integers = equilibriumAmountDisposal(nursingBo1);

		IntakeTableVo intakeTableVo = new IntakeTableVo();
		intakeTableVo.setStartTimeHour(startHour);

		if (CollectionUtils.isEmpty(dateShow)) {
			HashMap<String, List<List<IntakeOutputShow>>> output = collectIntakeAndOutput(null, ProjectTypeEnum.OUTPUT_PROJECT.getCode());
			HashMap<String, List<List<IntakeOutputShow>>> intake = collectIntakeAndOutput(null, ProjectTypeEnum.INTAKE_PROJECT.getCode());
			intakeTableVo.setIntakeShows(intake);
			intakeTableVo.setOutputShows(output);
			//已有出入量    护理人  平衡量
		} else {
			//分出出入量
			Map<Integer, List<IntakeOutputShow>> collect = dateShow.stream().collect(Collectors.groupingBy(IntakeOutputShow::getIntakeOutputType));
			//分项目
			List<IntakeOutputShow> inTake = collect.get(IntakeOutputEnum.INTAKE.getCode());//入量
			List<IntakeOutputShow> intakeOutputShows = disposalInput(inTake);
			List<IntakeOutputShow> outPut = collect.get(IntakeOutputEnum.OUTPUT.getCode());//出量
			HashMap<String, List<List<IntakeOutputShow>>> output = collectIntakeAndOutput(outPut, ProjectTypeEnum.OUTPUT_PROJECT.getCode());
			HashMap<String, List<List<IntakeOutputShow>>> intake = collectIntakeAndOutput(intakeOutputShows, ProjectTypeEnum.INTAKE_PROJECT.getCode());
			intakeTableVo.setIntakeShows(intake);
			intakeTableVo.setOutputShows(output);
		}

		intakeTableVo.setCountNum(integers);
		//是护理记录单一
		if (nursingBo.getNursingReportFlag() != null && nursingBo.getNursingReportFlag() == 1) {

			intakeTableVo.setIntakeCountVo(groupByTime(nursingBo1));

		}
		List<Project> projectList = projectService.selectByType(ProjectTypeEnum.INTAKE_PROJECT.getCode());
		List<Project> projectList1 = projectService.selectByType(ProjectTypeEnum.OUTPUT_PROJECT.getCode());
		HashMap<String, String> name = new HashMap<>();
		projectList.forEach(ar->{

			name.put(ar.getProjectCode(), ar.getProjectName());

		});

		projectList1.forEach(ar->{

			name.put(ar.getProjectCode(), ar.getProjectName());

		});
		intakeTableVo.setProjectName(name);
		return intakeTableVo;
	}






	/**
	 * 对出入量做项目的分组
	 *
	 * @param inTake
	 */
	private HashMap<String, List<List<IntakeOutputShow>>>
	collectIntakeAndOutput(List<IntakeOutputShow> inTake, Integer type) {

		List<Project> projectList = projectService.selectByType(type);
		HashMap<String, List<List<IntakeOutputShow>>> result = new HashMap<>();
		List<List<IntakeOutputShow>> other = new ArrayList<>();
		if (CollectionUtils.isEmpty(inTake)) {
			//结果为空时
			if (CollectionUtils.isEmpty(inTake)) {
				List<List<IntakeOutputShow>> integerListHashMap = this.collectByTimeHour(null);
				projectList.forEach(ar -> {
					if ((!ar.getProjectCode().equals("KFBS"))&&(!ar.getProjectCode().equals("JMRL"))&&
							(!ar.getProjectCode().equals("TL"))&&(!ar.getProjectCode().equals("NL"))) {

						other.addAll(integerListHashMap);
						return;

					}

					result.put(ar.getProjectCode(), integerListHashMap);
				});

				List<List<IntakeOutputShow>> other1 = other(other);
				if (CollectionUtils.isEmpty(other1)) {
					other1 = setData(null);
				}
				result.put("other", other1);
				return result;
			}
		}
		//按项目分组
		Map<String, List<IntakeOutputShow>> collect = inTake.stream()
				.collect(Collectors.groupingBy(IntakeOutputShow::getProjectId));
		List<List<IntakeOutputShow>> res = new ArrayList<>();
		//对数据作时间分组
		projectList.forEach(ar -> {
			if ((!ar.getProjectCode().equals("KFBS"))&&(!ar.getProjectCode().equals("JMRL"))&&
					(!ar.getProjectCode().equals("TL"))&&(!ar.getProjectCode().equals("NL"))) {

				res.addAll(collectByTimeHour(collect.get(ar.getProjectId())));
				return;

			}
			result.put(ar.getProjectCode(), collectByTimeHour(collect.get(ar.getProjectId())));
		});

		List<List<IntakeOutputShow>> other1 = other(res);

		if (CollectionUtils.isEmpty(other1)) {
			other1 = setData(null);
		}

		result.put("other", other1);

		return result;
	}

	/**
	 * 处理其它出入量
	 * @param other
	 * @return
	 */
	private List<List<IntakeOutputShow>> other(List<List<IntakeOutputShow>> other) {
		if (CollectionUtils.isEmpty(other)) {

			return other;

		}

		int size = other.size();
		if (size<=24) {
			return other;
		}
		for (int i = 24; i < size; i++) {

			List<IntakeOutputShow> intakeOutputShows = other.get(i % 24);

			List<IntakeOutputShow> intakeOutputShows1 = other.get(i);

			intakeOutputShows.addAll(intakeOutputShows1);

		}

		List<List<IntakeOutputShow>> lists = other.subList(0, 23);

		return lists;
	}

	/**
	 * 处理数据
	 * 主要是医嘱统一
	 * @param inTake
	 * @return
	 */
	private List<IntakeOutputShow> disposalInput(List<IntakeOutputShow> inTake) {

		if (CollectionUtils.isEmpty(inTake)) {
			return inTake;
		}
		//筛选出医嘱不为空的数据
		List<IntakeOutputShow> collect1 = inTake.stream().filter(ar -> !StringUtils.isEmpty(ar.getCauseRemark())).collect(Collectors.toList());
		//将按医嘱分组
		Map<String, List<IntakeOutputShow>> collect = collect1.stream().collect(Collectors.groupingBy(IntakeOutputShow::getCauseRemark));

		Set<String> strings = collect.keySet();

		Iterator<String> iterator = strings.iterator();

		int num = 1;
		//将医嘱替换成索引  重新封装
		while (iterator.hasNext()) {
			String next = iterator.next();
			List<IntakeOutputShow> intakeOutputShows = collect.get(next);
			for (int i = 0; i < intakeOutputShows.size(); i++) {
				intakeOutputShows.get(i).setCauseRemark(num + "");
			}

			num++;
		}

		return inTake;
	}


	/**
	 * 获取 24小时的 每小时的平衡量
	 *
	 * @param nursingBo
	 * @return
	 */
	private List<Integer> equilibriumAmountDisposal(NursingBo nursingBo) {
		//查询时间区间的数据
		List<IntakeOutputShow> intakeOutputShows = baseMapper.selectEquilibriumAmount(nursingBo);

		List<Integer> result = new ArrayList<>(24);
		//空对象
		for (int i = 0; i < 24; i++) {
			result.add(null);
		}
		//结果为空时  直接返回空对象
		if (CollectionUtils.isEmpty(intakeOutputShows)) {
			return result;
		}
		//将数据按小时分组
		Map<Integer, List<IntakeOutputShow>> collect = intakeOutputShows.stream().collect(Collectors.groupingBy(IntakeOutputShow::getHour));

		Set<Integer> integers = collect.keySet();
		//将查询的值在对应的替换掉对应的空值
		integers.forEach(ar -> {
			//将实际时间和集合索引对应
			int i = ar - startHour;

			int num = i < 0 ? i + 24 : i;

			List<IntakeOutputShow> intakeOutputShows1 = collect.get(ar);
			int res = 0;
			//计算每个小时的平衡量
			for (int j = 0; j < intakeOutputShows1.size(); j++) {

				res += (intakeOutputShows1.get(j).getIntakeOutputValue() == null ? 0 : intakeOutputShows1.get(j).getIntakeOutputValue());

			}

			result.set(num, res);

		});

		return result;

	}

	/**
	 * 对出入量做时间（小时）的分组
	 *
	 * @param listMap
	 */
	private List<List<IntakeOutputShow>> collectByTimeHour(List<IntakeOutputShow> listMap) {

		if (CollectionUtils.isEmpty(listMap)) {

			return setData(null);

		}
		//对单个项目的数据作小时分组
		Map<Integer, List<IntakeOutputShow>> collect = listMap
				.stream().collect(Collectors.groupingBy(IntakeOutputShow::getHour));

		return setData(collect);
	}

	/**
	 * 将查询到的数据 按时间放入
	 */
	private List<List<IntakeOutputShow>> setData(Map<Integer, List<IntakeOutputShow>> listMap) {
		//创建返回对象
		List<List<IntakeOutputShow>> result = new ArrayList<>();
		//对其赋24个空值
		for (int i = 0; i < 24; i++) {
			List<IntakeOutputShow> msg = new ArrayList<>();
			result.add(msg);
		}
		//当传来的结果为空时 直接返回空对象
		if (CollectionUtils.isEmpty(listMap)) {
			return result;
		}

		Set<Integer> integers = listMap.keySet();
		//将存在的数据 替换 掉空值
		integers.forEach(ar -> {

			int i = ar - startHour;

			int num = i < 0 ? i + 24 : i;

			result.set(num, listMap.get(ar));

		});

		return result;
	}


	/**
	 * 将出入量 平衡量数据  合计
	 * 用于护理记录单一的右侧展示
	 *
	 * @param nursingBo
	 * @return
	 */
	private IntakeCountVo groupByTime(NursingBo nursingBo) {


		IntakeCountVo intakeCountVo = new IntakeCountVo();

		HashMap<String, List<Integer>> intake = new HashMap<>();
		HashMap<String, List<Integer>> outPut = new HashMap<>();

		List<Integer> msg = new ArrayList<>(4);

		for (int i = 0; i < 4; i++) {
			msg.add(null);
		}

		LocalDateTime startTime = nursingBo.getStartTime();

		//第一班
		List<IntakeRemarksVo> one = groupByRemarks(nursingBo, startTime, startTime.minusHours(-(first - 1)));
		//第二班
		List<IntakeRemarksVo> two = groupByRemarks(nursingBo, startTime.minusHours(-first), startTime.minusHours(-first-second+1));
		//第三班
		List<IntakeRemarksVo> three = groupByRemarks(nursingBo, startTime.minusHours(-first - second), startTime.minusHours(-first - second-third+1));
		for (int i = 0; i < one.size() ; i++) {
			//创建集合存放结果
			List<Integer> ar = new ArrayList<>(4);
			//三个班次的第i+1行数据
			IntakeRemarksVo intakeRemarksVo = one.get(i);
			IntakeRemarksVo intakeRemarksVo1 = two.get(i);
			IntakeRemarksVo intakeRemarksVo2 = three.get(i);
			//每个班次该项目的统计数据
			Integer integer = (intakeRemarksVo.getCountNum() == null || intakeRemarksVo.getCountNum() == 0) ? null : intakeRemarksVo.getCountNum();
			Integer integer1 = (intakeRemarksVo1.getCountNum() == null || intakeRemarksVo1.getCountNum() == 0) ? null : intakeRemarksVo1.getCountNum();
			Integer integer2 = (intakeRemarksVo2.getCountNum() == null || intakeRemarksVo2.getCountNum() == 0) ? null : intakeRemarksVo2.getCountNum();
			//该项目的合计数据
			int i1 = (intakeRemarksVo.getCountNum() == null ? 0 : intakeRemarksVo.getCountNum())
					+ (intakeRemarksVo1.getCountNum() == null ? 0 : intakeRemarksVo1.getCountNum())
					+ (intakeRemarksVo2.getCountNum() == null ? 0 : intakeRemarksVo2.getCountNum());
			ar.add(integer);
			ar.add(integer1);
			ar.add(integer2);
			ar.add(i1 == 0 ? null : i1);
			//类型为空  这是平衡量
			if (intakeRemarksVo.getIntakeOutputType() == null) {

				intakeCountVo.setEquilibriumAmount(ar);
				continue;

			}
			if (ProjectTypeEnum.INTAKE_PROJECT.getCode().equals(intakeRemarksVo.getIntakeOutputType())) {

				intake.put(intakeRemarksVo.getProjectCode(), ar);

			} else if (ProjectTypeEnum.OUTPUT_PROJECT.getCode().equals(intakeRemarksVo.getIntakeOutputType())) {
				outPut.put(intakeRemarksVo.getProjectCode(), ar);
			}


		}

		intakeCountVo.setIntake(intake);
		intakeCountVo.setOutPut(outPut);

		return intakeCountVo;
	}

	/**
	 * 对每班的出入量做处理
	 *
	 * @param nursingBo
	 * @param s
	 * @param e
	 */
	private List<IntakeRemarksVo> groupByRemarks(NursingBo nursingBo, LocalDateTime s, LocalDateTime e) {

		nursingBo.setStartTime(s);

		nursingBo.setEndTime(e);
		//查询该班次的出入量统计数据 按项目区分
		List<IntakeRemarksVo> count = baseMapper.getCounts(nursingBo);
		//将备注为胶体的数据 筛选出来
		List<IntakeRemarksVo> collect = count.stream().filter(user -> ("胶体").equals(user.getRemarks())).collect(Collectors.toList());

		List<IntakeRemarksVo> other = count.stream().filter(user -> !("胶体").equals(user.getRemarks())).collect(Collectors.toList());

		List<IntakeRemarksVo> result = new ArrayList<>(10);
		IntakeRemarksVo intakeRemarksVo = new IntakeRemarksVo();
		for (int i = 0; i < 9; i++) {
			result.add(intakeRemarksVo);
		}
		//胶体项目不存在时
		if (CollectionUtils.isEmpty(collect)) {
			//存入一个空的胶体数据
			IntakeRemarksVo msg = new IntakeRemarksVo();
			msg.setIntakeOutputType(ProjectTypeEnum.INTAKE_PROJECT.getCode());
			msg.setProjectName("胶体");
			msg.setProjectCode("JIAOT");
			msg.setCountNum(0);
			result.set(0, msg);
		} else {
			//将胶体数据直接存入
			collect.get(0).setProjectName("胶体");
			collect.get(0).setProjectCode("JIAOT");
			result.set(0, collect.get(0));
		}
		//将非胶体的出入量 按出入量类型分组
		Map<Integer, List<IntakeRemarksVo>> collect1 = other.stream().collect(Collectors.groupingBy(IntakeRemarksVo::getIntakeOutputType));
		//入量数据
		List<IntakeRemarksVo> intakeRemarksVos = collect1.get(ProjectTypeEnum.INTAKE_PROJECT.getCode());

		int size1 = intakeRemarksVos.size();//入量的项目数
		int intNum = 0;//入量的总计

		IntakeRemarksVo ty = new IntakeRemarksVo();
		ty.setProjectName("other");
		ty.setProjectCode("other");
		ty.setIntakeOutputType(3);
		int a = 0;
		//晶体的数据合计
		for (int i = 0; i < size1; i++) {

			IntakeRemarksVo ar = intakeRemarksVos.get(i);
			if ("JMRL".equals(ar.getProjectCode())) {
				ar.setProjectName("晶体");
				ar.setProjectCode("JINGT");
				result.set(1, ar);
				intNum += (ar.getCountNum() == null ? 0 : ar.getCountNum());
			} else if ("KFBS".equals(ar.getProjectCode())) {
				result.set(2, ar);
				intNum += (ar.getCountNum() == null ? 0 : ar.getCountNum());
			} else {
				a += (ar.getCountNum() == null ? 0 : ar.getCountNum());
				intNum += (ar.getCountNum() == null ? 0 : ar.getCountNum());
			}
		}

		ty.setCountNum(a);
		result.set(3, ty);

		//存入入量的合计
		IntakeRemarksVo one = new IntakeRemarksVo();
		one.setCountNum(intNum);
		one.setIntakeOutputType(ProjectTypeEnum.INTAKE_PROJECT.getCode());
		one.setProjectName("入量合计");
		one.setProjectCode("RLHJ");
		result.set(4, one);
		//出量的数据
		List<IntakeRemarksVo> out = collect1.get(ProjectTypeEnum.OUTPUT_PROJECT.getCode());
		//出量合计的变量
		int outNum = 0;
		IntakeRemarksVo res = new IntakeRemarksVo();
		res.setProjectName("other");
		res.setProjectCode("other");
		res.setIntakeOutputType(2);
		int c = 0;
		//按项目存入项目合计数据
		for (int i = 0; i < out.size(); i++) {

			IntakeRemarksVo ar = out.get(i);

			if ("TL".equals(ar.getProjectCode())) {
				result.set(5, ar);
			} else if ("NL".equals(ar.getProjectCode())) {
				result.set(6, ar);
			} else {
				c += (ar.getCountNum() == null ? 0 : ar.getCountNum());
			}
			res.setCountNum(c);
			result.set(7, res);
			outNum += (ar.getCountNum() == null ? 0 : ar.getCountNum());

		}
		//出量合计
		IntakeRemarksVo two = new IntakeRemarksVo();
		two.setCountNum(outNum);
		two.setIntakeOutputType(ProjectTypeEnum.OUTPUT_PROJECT.getCode());
		two.setProjectName("出量合计");
		two.setProjectCode("CLHJ");
		result.set(8, two);
		//平衡量数据
		IntakeRemarksVo three = new IntakeRemarksVo();
		three.setIntakeOutputType(null);
		three.setProjectName("平衡量");
		three.setProjectCode("PHL");
		three.setCountNum(outNum + intNum);
		//将一个班次的统计数据返回
		result.add(three);
		return result;

	}


	/**
	 * 获取该患者的出入量数据(图标展示 24小时)
	 *
	 * @param nursingBo
	 * @return
	 */
	@Override
	public List<IntakeOutputShow> getDateShow(NursingBo nursingBo) {

		if (nursingBo.getStartTime() == null) {
			//当开始时间为空时，说明查询的是图表数据
			NursingBo nursingBo1 = this.timeDisposal(nursingBo);
			List<IntakeOutputShow> dateShow = baseMapper.getDateShow(nursingBo1);
			if (CollectionUtils.isEmpty(dateShow)) {
				return dateShow;
			}
			dateShow.addAll(disposalMap(dateShow));
			return dateShow;
		}

		List<IntakeOutputShow> dateShow = baseMapper.getDateShow(nursingBo);

		return dateShow;
	}



	private List<IntakeOutputShow> disposalMap(List<IntakeOutputShow> dateShow) {



		if (CollectionUtils.isEmpty(dateShow)) {
			return null;
		}

		List<IntakeOutputShow> list = new ArrayList<>(2);
		for (int i = 0; i < 2; i++) {
			IntakeOutputShow intakeOutputShow = new IntakeOutputShow();
			intakeOutputShow.setIntakeOutputType(2+i);
			list.add(intakeOutputShow);
		}

		Map<Integer, List<IntakeOutputShow>> collect = dateShow.stream().collect(Collectors.groupingBy(IntakeOutputShow::getIntakeOutputType));
		List<IntakeOutputShow> intake = collect.get(IntakeOutputEnum.INTAKE.getCode());
		List<IntakeOutputShow> output = collect.get(IntakeOutputEnum.OUTPUT.getCode());
		if (!CollectionUtils.isEmpty(intake)) {
			int num = 0;
			for (int i = 0; i < intake.size(); i++) {
				num += intake.get(i).getIntakeOutputValue();
			}
			list.get(0).setIntakeOutputValue(num);
		}

		if (!CollectionUtils.isEmpty(output)) {
			int num = 0;
			for (int i = 0; i < output.size(); i++) {
				num += output.get(i).getIntakeOutputValue();
			}
			list.get(1).setIntakeOutputValue(num);
		}

		return list;

	}



	/**
	 * 修改数据
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public boolean updateRecord(IntakeOutputRecord intakeOutputRecord) {

		//查询该出入的原数据
		IntakeOutputRecord byId = this.getById(intakeOutputRecord.getId());
		Integer newEquilibriumAmount = this.getNewEquilibriumAmount(byId);
		//判断出入的值是否发生改变
		if (intakeOutputRecord.getIntakeOutputValue().equals(byId.getIntakeOutputValue())) {
			//出入量值 未改变  直接修改
			return this.updateById(intakeOutputRecord);
		}
		//当平衡量不为空时  将此次的出入量 加到平衡量中  为空则将出入量作为平衡量
		intakeOutputRecord.setEquilibriumAmount((newEquilibriumAmount == null ? 0 : newEquilibriumAmount) + intakeOutputRecord.getIntakeOutputValue());
		intakeOutputRecord.getValueDescription().replace(Math.abs(byId.getIntakeOutputValue())+"", Math.abs(byId.getIntakeOutputValue())+"");
		//进行修改该条数据
		boolean b = this.updateById(intakeOutputRecord);
		//查询某条数据之后的数据
		List<IntakeOutputRecord> intakeOutputRecords = baseMapper.selectAfter(intakeOutputRecord);
		//后面不存在数据时  直接返回结果
		if (CollectionUtils.isEmpty(intakeOutputRecords)) {
			return b;
		}
		//获取现在的平衡量
		Integer num = intakeOutputRecord.getEquilibriumAmount();
		//将数据逐条修改
		for (int i = 0; i < intakeOutputRecords.size(); i++) {
			IntakeOutputRecord ar = intakeOutputRecords.get(i);
			ar.setEquilibriumAmount(num + ar.getIntakeOutputValue());
			num = ar.getEquilibriumAmount();
		}
		return this.updateBatchById(intakeOutputRecords);
	}

	/**
	 * 删除数据
	 * @param intakeOutputRecord
	 * @return
	 */
	@Override
	public boolean delete(IntakeOutputRecord intakeOutputRecord) {

		//查询某条数据之后的数据
		List<IntakeOutputRecord> intakeOutputRecords = baseMapper.selectAfter(intakeOutputRecord);

		//后面数据为空  直接删除
		if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(intakeOutputRecords)) {

			return this.updateById(intakeOutputRecord);

		}
		//获取到该数据的出入量
		Integer num = intakeOutputRecord.getIntakeOutputValue();
		//对数据进行修改
		for (int i = 0; i < intakeOutputRecords.size(); i++) {
			IntakeOutputRecord ar = intakeOutputRecords.get(i);
			ar.setEquilibriumAmount(ar.getEquilibriumAmount() - num);
		}
		//删除该条数据
		this.updateById(intakeOutputRecord);
		//修改后续数据
		return this.updateBatchById(intakeOutputRecords);

	}
}
