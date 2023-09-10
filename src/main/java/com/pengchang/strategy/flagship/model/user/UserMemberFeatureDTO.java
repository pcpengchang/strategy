package com.pengchang.strategy.flagship.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengchang
 * @description: 用户会员信息
 * @date 2023/08/31
 */
@Data
public class UserMemberFeatureDTO implements Serializable {

    private static final long serialVersionUID = -7046560353016598895L;

    /**
     * 是否是会员
     */
    private boolean memberFeature = false;
}
