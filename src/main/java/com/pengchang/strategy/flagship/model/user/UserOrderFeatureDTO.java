package com.pengchang.strategy.flagship.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengchang
 * @description: 订单行为信息特征
 * @date 2023/08/31
 */
@Data
public class UserOrderFeatureDTO implements Serializable {

    private static final long serialVersionUID = -8961667128460675739L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单类型
     */
    private Integer orderStatus;

    /**
     * 订单价格
     */
    private Double orderPrice;

    /**
     * 订单实际支付时间
     */
    private Long realPayTime;

    /**
     * 订单poiId
     */
    private Long poiId;
}
