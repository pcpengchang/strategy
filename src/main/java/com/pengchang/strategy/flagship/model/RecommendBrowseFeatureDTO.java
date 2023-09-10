package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 旗舰店推荐浏览特征信息
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Setter
@Getter
@ToString
public class RecommendBrowseFeatureDTO implements Serializable {
    private static final long serialVersionUID = 7506359633740790022L;

    /**
     * 浏览规则是否开启
     */
    private boolean open;

    /**
     * 浏览数据回溯天数,默认为7
     */
    private int traceDay = 7;
}
