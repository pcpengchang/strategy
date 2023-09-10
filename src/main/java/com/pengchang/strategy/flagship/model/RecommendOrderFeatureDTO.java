package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 旗舰店推荐订单信息特征
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Setter
@Getter
@ToString
public class RecommendOrderFeatureDTO implements Serializable {

    private static final long serialVersionUID = 4573190656538356804L;

    /**
     * 用户是否需要判断订单
     */
    private boolean open;

    /**
     * 订单回溯天数
     */
    private int traceDay;

    /**
     * 订单上限价
     */
    private int orderPriceMax = Integer.MAX_VALUE;

    /**
     * 订单下限价
     */
    private int orderPriceMin;

    /**
     * 订单类型
     */
    private int orderStatus;
}
