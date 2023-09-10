package com.pengchang.strategy.flagship.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengchang
 * @description: 用户展示行为信息
 * @date 2023/08/31
 */
@Data
public class UserDisplayFeatureDTO implements Serializable {
    private static final long serialVersionUID = 2608361270915179864L;

    /**
     * 用户点选的次数
     */
    private int usedNum;

    /**
     * 开始展示时间
     */
    private long startTime;
}
