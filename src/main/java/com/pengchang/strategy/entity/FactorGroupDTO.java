package com.pengchang.strategy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author pengchang
 * @date 2023/08/19 00:02
 **/
@Data
@Accessors(chain = true)
public class FactorGroupDTO {
    /**
     * 外层的因子关系为或，内层的因子关系为且
     */
    private List<List<Long>> factorIds;
}
