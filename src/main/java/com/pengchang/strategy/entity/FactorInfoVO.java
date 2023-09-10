package com.pengchang.strategy.entity;

import com.pengchang.strategy.enums.OperatorTypeEnum;
import com.pengchang.strategy.enums.VarTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author pengchang
 * @date 2023/08/18 23:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FactorInfoVO {
    /**
     * 主键id
     */
    private Long id;
//    /**
//     * 因子模板id
//     */
//    private Long templateId;
//    /**
//     * 变量配置, 比如传入id Map<"ids", "1001,1002">
//     */
//    private Map<String, String> variableInfo;
    /**
     * 操作符类型
     */
    private OperatorTypeEnum operatorTypeEnum;
    /**
     * 操作数
     */
    private String operand;

    /**
     * 返回值类型 todo 后续扩充为因子模板
     */
    private VarTypeEnum resultTypeEnum;
}