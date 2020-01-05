package com.pig4cloud.pigx.ccxxicu.api.vo.knowledgeBase;

import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.PersonalSummary;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2019/11/4 0004.
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "个人总结展示")
public class PersonalSummaryVo extends PersonalSummary{
    /**
     * 创建人姓名
     */
    @ApiModelProperty(value="创建人姓名")
    private String nurseName;
    /**
     * 是否收藏标识 有值表示已收藏，null表示未收藏
     */
    @ApiModelProperty(value="是否收藏标识 有值表示已收藏，null表示未收藏")
    private String collectStatus;

    /**
     * 护士id
     */
    @ApiModelProperty(value="护士id")
    private String nurseId;
}
