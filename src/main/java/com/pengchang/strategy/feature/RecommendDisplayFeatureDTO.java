package com.pengchang.strategy.feature;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author pengchang
 * @date 2023/08/27 09:55
 **/
@Data
@ToString
public class RecommendDisplayFeatureDTO implements Serializable {
    private static final long serialVersionUID = -2019134542439366889L;

    /**
     * 用户是否勾选展示行为
     */
    private boolean open = true;

//    /**
//     * 最大展示次数
//     */
//    private int maxDisplayTimes;
}
