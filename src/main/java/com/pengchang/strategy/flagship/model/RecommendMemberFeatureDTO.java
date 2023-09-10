package com.pengchang.strategy.flagship.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 旗舰店推荐会员特征信息
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Setter
@Getter
@ToString
public class RecommendMemberFeatureDTO implements Serializable {

    private static final long serialVersionUID = 1695769239335160962L;

    /**
     * 会员规则是否开启
     */
    private boolean open;

    /**
     * 用户范围，0为所有用户，默认1为会员用户，2为非会员用户
     */
    private int userScope = 1;

}
