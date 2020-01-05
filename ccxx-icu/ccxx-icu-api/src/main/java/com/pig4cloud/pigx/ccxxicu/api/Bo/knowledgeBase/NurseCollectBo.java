package com.pig4cloud.pigx.ccxxicu.api.Bo.knowledgeBase;

import com.pig4cloud.pigx.ccxxicu.api.entity.knowledgeBase.NurseCollect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2019/11/4 0004.
 */
@Data
@ApiModel(value = "个人总结收藏与护士关联")
public class NurseCollectBo extends NurseCollect{
    /**
     * 收藏的标识，0:收藏  1:取消收藏
     */
    @ApiModelProperty(value="收藏的标识，0:收藏  1:取消收藏")
    private Integer collectFlag;
}
