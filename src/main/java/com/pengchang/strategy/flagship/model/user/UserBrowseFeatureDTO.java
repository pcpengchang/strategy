package com.pengchang.strategy.flagship.model.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengchang
 * @description: 用户浏览行为信息
 * @date 2023/08/31
 */
@Data
public class UserBrowseFeatureDTO implements Serializable {
    private static final long serialVersionUID = -8450239158779365225L;

    /**
     * 用户浏览时间
     */
    private Long browseActionTime;

    /**
     * 浏览poiId
     */
    private Long poiId;
}
