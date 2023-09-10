package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 旗舰店推荐展示策略特征
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Setter
@Getter
@ToString
public class RecommendDisplayFeatureDTO implements Serializable {

    private static final long serialVersionUID = -527156828781531495L;

    /**
     * 用户是否勾选展示行为
     */
    private boolean open;

    /**
     * 最大展示次数
     */
    private int maxDisplayTimes;

    /**
     * 最长展示时间，单位天
     */
    private int maxDisplayTime;

    /**
     * 生效起始时间
     */
    private long startTime;

    /**
     * 生效终止时间
     */
    private long endTime;
}
