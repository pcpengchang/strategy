package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 旗舰店推荐特征数据
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Setter
@Getter
@ToString
public class FlagshipRecommendFeatureDTO implements Serializable {
    private static final long serialVersionUID = -2019134542439366889L;

    /**
     * 订单特征
     */
    private RecommendOrderFeatureDTO orderFeature;

    /**
     * 会员特征
     */
    private RecommendMemberFeatureDTO memberFeature;

    /**
     * 浏览特征
     */
    private RecommendBrowseFeatureDTO browseFeature;

    /**
     * 展示特征
     */
    private RecommendDisplayFeatureDTO displayFeature;

    /**
     * 推荐引擎
     */
    private RecommendEngineDTO engine;

}
