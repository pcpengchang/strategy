package com.pengchang.strategy.entity;

import com.google.common.collect.ImmutableMap;
import com.pengchang.strategy.enums.FactorTemplateTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 因子取数模型
 **/
@Data
@Accessors(chain = true)
public class FactorFetchDTO {
    /**
     * 因子id
     */
    private Long factorId = 1L;
    /**
     * 返回值类型
     */
    private VarTypeEnum resultTypeEnum = VarTypeEnum.BOOLEAN;
    /**
     * 实例配置的变量信息
     */
    private Map<String, String> variableMap = ImmutableMap.of("ids", "1,2");
    /**
     * 模板类型
     */
    private FactorTemplateTypeEnum factorTemplateTypeEnum = FactorTemplateTypeEnum.TIME;
}
