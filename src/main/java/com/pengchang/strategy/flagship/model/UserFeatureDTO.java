package com.pengchang.strategy.flagship.model;

import com.pengchang.strategy.flagship.model.user.UserBrowseFeatureDTO;
import com.pengchang.strategy.flagship.model.user.UserDisplayFeatureDTO;
import com.pengchang.strategy.flagship.model.user.UserMemberFeatureDTO;
import com.pengchang.strategy.flagship.model.user.UserOrderFeatureDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 用户特征信息
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Getter
@Setter
@ToString
public class UserFeatureDTO implements Serializable {
    private static final long serialVersionUID = -1492578277692826984L;

    /**
     * 近90天用户订单行为信息
     */
    private List<UserOrderFeatureDTO> userOrderFeatures;

    /**
     * 近7天用户浏览行为信息
     */
    private List<UserBrowseFeatureDTO> userBrowseFeatures;

    /**
     * 用户会员行为信息
     */
    private UserMemberFeatureDTO userMemberFeature;

    /**
     * 用户展示行为信息
     */
    private UserDisplayFeatureDTO userDisplayFeature;

}
