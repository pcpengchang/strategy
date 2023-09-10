package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 缓存模型
 *
 * @author pengchang
 * @date 2023/9/9
 */
@Getter
@Setter
@ToString
public class FlagshipRecommendCacheDTO implements Serializable {

    private static final long serialVersionUID = -7276696417843415273L;

    /**
     * 特征值
     */
    private FlagshipRecommendFeatureDTO feature;

    /**
     * 在线状态
     */
    private int onlineStatus;
}
