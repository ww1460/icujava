package com.pig4cloud.pigx.ccxxicu.service.scenes;

import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataInput;
import com.pig4cloud.pigx.ccxxicu.api.entity.cardpcscenes.ScenesDataReturn;
import com.pig4cloud.pigx.ccxxicu.api.entity.collection.CollectionRecord;

/**
 * 卡片电脑各种场景处理服务
 * @author liyadong
 * @date 2019.9.13
 */

public interface ScenesOperationService {

	ScenesDataReturn common(ScenesDataInput scenesDataInput);

	ScenesDataReturn medication(ScenesDataInput scenesDataInput);

	ScenesDataReturn pharmacy(ScenesDataInput scenesDataInput);

	ScenesDataReturn medicalWaste(ScenesDataInput scenesDataInput);

	ScenesDataReturn pMove(ScenesDataInput scenesDataInput);

	ScenesDataReturn mMove(ScenesDataInput scenesDataInput);

	ScenesDataReturn equipment(ScenesDataInput scenesDataInput);

	ScenesDataReturn collectData(CollectionRecord record);
}
